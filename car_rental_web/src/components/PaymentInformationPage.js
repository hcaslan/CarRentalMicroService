import React, { useState } from 'react';
import './PaymentPage.css';
import { useLocation, useNavigate } from 'react-router-dom';

const PaymentInformationPage = () => {
    const [cardNumber, setCardNumber] = useState('');
    const [cardHolder, setCardHolder] = useState('');
    const [expiryDate, setExpiryDate] = useState('');
    const [cvv, setCvv] = useState('');
    const location = useLocation();
    const navigate = useNavigate();

    const query = new URLSearchParams(location.search);
    const reservationData = Object.fromEntries(query.entries());

    const handlePaymentSubmit = (e) => {
        e.preventDefault();
        // Here, you can integrate with a payment gateway or API to process the payment

        // For now, we'll just log the payment details and reservation data
        console.log({
            cardNumber,
            cardHolder,
            expiryDate,
            cvv,
            reservationData
        });

        // Navigate to a confirmation page or show a success message
        navigate('/confirmation');
    };

    return (
        <div className="payment-page">
            <header className="header">
                <div className="logo">
                    <a href="/welcome"><img src="/logo.png" alt="Logo" /></a>
                    <a href="/welcome">HCA</a>
                </div>
                <ul className="nav-list">
                    <li><a href="/rezervations">Reservation Management</a></li>
                    <li><a href="/inventory">Cars</a></li>
                    <li><a href="/locations">Our Offices</a></li>
                </ul>
                <div className="user-actions">
                    <a href="/login" className="login">Login</a>
                    <a href="/register" className="register">Register</a>
                </div>
            </header>
            <main className="main-content">
                <div className="reservation-summary">
                    <h2>Reservation Summary</h2>
                    <p><strong>Pick Up And Drop Off Office:</strong> {reservationData.pickupOffice} - {reservationData.dropoffOffice}</p>
                    <p><strong>Pick Up:</strong> {reservationData.pickupDate} - {reservationData.pickupTime}</p>
                    <p><strong>Drop Off:</strong> {reservationData.dropoffDate} - {reservationData.dropoffTime}</p>
                    <p><strong>Location:</strong> {reservationData.car_location}</p>
                    <p><strong>Car ID:</strong> {reservationData.carId}</p>
                </div>
                <h1>Payment Information</h1>
                <form className="payment-form" onSubmit={handlePaymentSubmit}>
                    <label>
                        Card Number:
                        <input
                            type="text"
                            value={cardNumber}
                            onChange={(e) => setCardNumber(e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Card Holder:
                        <input
                            type="text"
                            value={cardHolder}
                            onChange={(e) => setCardHolder(e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Expiry Date:
                        <input
                            type="month"
                            value={expiryDate}
                            onChange={(e) => setExpiryDate(e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        CVV:
                        <input
                            type="text"
                            value={cvv}
                            onChange={(e) => setCvv(e.target.value)}
                            required
                        />
                    </label>
                    <button type="submit">Confirm Payment</button>
                </form>
            </main>
        </div>
    );
};

export default PaymentInformationPage;
