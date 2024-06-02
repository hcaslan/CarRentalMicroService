import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import './Cars.css';
import './Header.css';
import { useLocation, useNavigate } from 'react-router-dom';

const MakeReservationPage = () => {
    const [cars, setCars] = useState([]);
    const [page, setPage] = useState(0);
    const [size] = useState(20);
    const [totalPages, setTotalPages] = useState(0);
    const location = useLocation();
    const navigate = useNavigate();
    const [dayCount, setDayCount] = useState(0);
    
    const query = new URLSearchParams(location.search);
    const pickupOffice = query.get('pickupOffice');
    const dropoffOffice = query.get('dropoffOffice');
    const pickupDate = query.get('pickupDate');
    const pickupTime = query.get('pickupTime');
    const dropoffDate = query.get('dropoffDate');
    const dropoffTime = query.get('dropoffTime');
    const car_location = query.get('car_location');

    useEffect(() => {
        // Check if the previous page was the welcome page
        const referrer = document.referrer;
        const isFromApp= referrer && (referrer.includes('/welcome') || referrer.includes('/inventory'));
        if (!isFromApp) {
            navigate('/welcome');
        }
    }, [navigate]);


    useEffect(() => {
        // Calculate the number of days between pickup and dropoff dates
        if (pickupDate && dropoffDate) {
            const pickup = new Date(pickupDate);
            const dropoff = new Date(dropoffDate);
            const diffTime = Math.abs(dropoff - pickup);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
            setDayCount(diffDays);
        }
    }, [pickupDate, dropoffDate]);

    // Fetch cars
    const fetchCars = useCallback(() => {
        axios.get('http://localhost:3004/api/v1/search/cars/filter', {
            params: { page, size }
        })
            .then(response => {
                setCars(response.data.content);
                setTotalPages(response.data.totalPages);
            })
            .catch(error => {
                console.error('There was an error fetching the car data!', error);
            });
    }, [page, size]);

    useEffect(() => {
        fetchCars();
    }, [fetchCars]);

    const handleRentNow = (carId) => {
        const reservationData = {
            carId,
            pickupOffice,
            dropoffOffice,
            pickupDate,
            pickupTime,
            dropoffDate,
            dropoffTime,
            car_location
        };
        
        // Redirect to the reservation confirmation page or another endpoint
        navigate(`/payment-information?${new URLSearchParams(reservationData).toString()}`);
    };
    const handlePastStep = () => {
        navigate(`/welcome`);
    };
    const handleCurrentStep = () => {

    };
    const handleNextStep = () => {

    };
    return (
        <div className="car-list-page">
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
                <div className="reservation-info">
                    <h2>Your Reservation {dayCount > 0 ? dayCount : 'X'} DAY(S) </h2>
                    <p><strong>Pick Up And Drop Off Office:</strong> {pickupOffice} - {dropoffOffice}</p>
                    <p><strong>Pick Up:</strong> {pickupDate} - {pickupTime}</p>
                    <p><strong>Drop Off:</strong> {dropoffDate} - {dropoffTime}</p>
                    <p><strong>Location:</strong> {car_location}</p>
                    <div className="reservation-steps">
                        <button className="past-step" onClick={() => handlePastStep()}>Reservation Details</button>
                        <button className="current-step" onClick={() => handleCurrentStep()}>Available Cars</button>
                        <button className="next-step" onClick={() => handleNextStep()}>Payment Information</button>
                    </div>
                </div>
                <h1>Available Cars for Rent</h1>
                <div className="car-list">
                    {cars.length === 0 && <p>No cars available.</p>}
                    {cars.map(car => (
                        <div key={car.id} className="car-card">
                            <div className="card-image-container">
                                <img src={`/${car.image}`} alt={car.name} />
                            </div>
                            <div className="car-details">
                                <h2>{car.name}</h2>
                                <div className="car-properties">
                                    <p><strong>Model Year:</strong> {car.modelYear}</p>
                                    <p><strong>Category:</strong> {car.category}</p>
                                    <p><strong>Gear Type:</strong> {car.gearType}</p>
                                    <p><strong>Fuel Type:</strong> {car.fuelType}</p>
                                    <p><strong>Seats:</strong> {car.seats}</p>
                                    <p><strong>Daily Price:</strong> ${car.dailyPrice}</p>
                                    <p className="total-price"><strong>Total Price:</strong> ${(car.dailyPrice * dayCount).toFixed(2)}</p>
                                </div>
                                <button onClick={() => handleRentNow(car.id)}>Select</button>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="pagination-controls">
                    <label>
                        Page:
                        <input
                            type="number"
                            value={page + 1}
                            onChange={(e) => setPage(Number(e.target.value - 1))}
                            min="1"
                            max={totalPages}
                        />
                    </label>
                    <button onClick={() => setPage(prev => Math.max(prev - 1, 0))} disabled={page === 0}>Previous</button>
                    <button onClick={() => setPage(prev => (prev < totalPages - 1 ? prev + 1 : prev))} disabled={page >= totalPages - 1}>Next</button>
                </div>
            </main>
        </div>
    );
};

export default MakeReservationPage;
