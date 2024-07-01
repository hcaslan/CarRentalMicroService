import React, { useState, useEffect  } from 'react';
import './Header.css';
import './Form.css';
import './PaymentPage.css';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {jwtDecode} from "jwt-decode";

const PaymentInformationPage = () => {
    const [carDetails, setCarDetails] =  useState('');
    const [cardNumber, setCardNumber] = useState('');
    const [cardHolder, setCardHolder] = useState('');
    const [expiryDate, setExpiryDate] = useState('');
    const [cvv, setCvv] = useState('');
    const location = useLocation();
    const navigate = useNavigate();
    const token = localStorage.getItem('token');

    const query = new URLSearchParams(location.search);
    const reservationData = Object.fromEntries(query.entries());
    const carId = reservationData.carId;
    const startDate = reservationData.pickupDate + ' ' + reservationData.pickupTime;
    const endDate = reservationData.dropoffDate + ' ' + reservationData.dropoffTime;
    const pickupOffice = reservationData.pickupOffice;
    const dropoffOffice = reservationData.dropoffOffice;

    useEffect(() => {
        const fetchCarDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:3004/api/v1/search/cars/findById/${carId}`);
                if (response.status === 200) {
                    console.log('API response:', response.data);
                    setCarDetails(response.data);
                    console.log('Car details:', carDetails);
                } else {
                    console.error('Error fetching car details:', response.data);
                }
            } catch (error) {
                console.error('Network error:', error);
            }
        };

        fetchCarDetails();
    }, [carId]);

    const handlePaymentSubmit = async (e) => {
        e.preventDefault();

        try {
            if (token) {
                try {
                    const decodedToken = jwtDecode(token);
                    console.log('Decoded token:', decodedToken);

                    const currentTime = Date.now() / 1000;
                    console.log('Current time (in seconds):', currentTime);

                    if (decodedToken.exp > currentTime) {
                        console.log('Token is valid, navigating to payment information...');
                        navigate(`/payment-information?${new URLSearchParams(reservationData).toString()}`);
                    } else {
                        console.log('Token is expired, removing token from localStorage and redirecting to login...');
                        localStorage.removeItem('token');
                        localStorage.setItem('reservationData', JSON.stringify(reservationData));
                        navigate(`/login?returnUrl=/payment-information&message=Your session has expired, please log in again.`);
                    }
                } catch (error) {
                    console.error('Error decoding token:', error);
                    localStorage.removeItem('token');
                    localStorage.setItem('reservationData', JSON.stringify(reservationData));
                    navigate(`/login?returnUrl=/payment-information&message=An error occurred, please log in again.`);
                }
            } else {
                console.log('No token found, saving reservation data and redirecting to login...');
                localStorage.setItem('reservationData', JSON.stringify(reservationData));
                navigate(`/login?returnUrl=/payment-information&message=Please log in to complete your reservation.`);
            }
            const response = await axios.post('http://localhost:3003/api/v1/inventory/rental/save', {
                token, carId, startDate, endDate, pickupOffice, dropoffOffice
            });

            if (response.status === 200) {
                console.log('Rental data saved successfully.');
                navigate('/rental-confirmation');
            } else {
                console.error('Error saving rental data:', response.data);
                navigate('/rental-error', { state: { message: response.data.message || 'Error saving rental data.' } });
            }
        } catch (error) {
            console.error('Network error:', error);
            navigate('/rental-error', { state: { message: error.message || 'Network error.' } });
        }
    };

    return (
        <div className="page">
            <header className="header">
                <div className="logo">
                    <a href="/welcome"><img src="/logo.png" alt="Logo" /></a>
                    <a href="/welcome">HCA</a>
                </div>
                <ul className="nav-list">
                    <li><a href="/reservations">Reservation Management</a></li>
                    <li><a href="/inventory">Cars</a></li>
                    <li><a href="/locations">Our Offices</a></li>
                </ul>
                <div className="user-actions">
                    <a href="/login" className="login">Login</a>
                    <a href="/register" className="register">Register</a>
                </div>
            </header>
            <main className="main-content">
                <div className="content">
                    <div className="reservation-summary">
                        <h2>Reservation Summary</h2>
                        <div className="summary-item">
                            <span className="label">Pick Up Office:</span>
                            <span className="value">{pickupOffice}</span>
                        </div>
                        <div className="summary-item">
                            <span className="label">Drop Off Office:</span>
                            <span className="value">{dropoffOffice}</span>
                        </div>
                        <div className="summary-item">
                            <span className="label">Pick Up:</span>
                            <span className="value">{reservationData.pickupDate} - {reservationData.pickupTime}</span>
                        </div>
                        <div className="summary-item">
                            <span className="label">Drop Off:</span>
                            <span className="value">{reservationData.dropoffDate} - {reservationData.dropoffTime}</span>
                        </div>
                        <div className="summary-item">
                            <span className="label">Location:</span>
                            <span className="value">{reservationData.car_location}</span>
                        </div>
                        <div className="summary-item">
                            <span className="label">Car:</span>
                            <span className="value">{carDetails ? carDetails.name : 'Loading...'}</span>
                        </div>
                    </div>

                    <form className="payment-form" onSubmit={handlePaymentSubmit}>
                        <h1>Payment Information</h1>
                        <div className="form-group">
                        <label>Card Number:</label>
                            <input
                                type="text"
                                value={cardNumber}
                                onChange={(e) => setCardNumber(e.target.value)}
                                required
                                className="input"
                            />
                        </div>
                        <div className="form-group">
                            <label>Card Holder:</label>
                            <input
                                type="text"
                                value={cardHolder}
                                onChange={(e) => setCardHolder(e.target.value)}
                                required
                                className="input"
                            />
                        </div>
                        <div className="form-group">
                            <label>Expiry Date:</label>
                            <input
                                type="month"
                                value={expiryDate}
                                onChange={(e) => setExpiryDate(e.target.value)}
                                required
                                className="input"
                            />
                        </div>
                        <div className="form-group">
                            <label>CVV:</label>
                            <input
                                type="text"
                                value={cvv}
                                onChange={(e) => setCvv(e.target.value)}
                                required
                                className="input"
                            />
                        </div>
                        <button type="submit">Confirm Payment</button>
                    </form>
                </div>
            </main>
        </div>
    );
};

export default PaymentInformationPage;
