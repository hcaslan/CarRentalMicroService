/* eslint-disable react/jsx-no-comment-textnodes */
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Form.css';
import './Header.css';

const WelcomePage = () => {
    const [offices, setOffices] = useState([]);
    const [pickupDate, setPickupDate] = useState('');
    const [dropoffDate, setDropoffDate] = useState('');
    const [dayCount, setDayCount] = useState(0);

    useEffect(() => {
        // Fetch the list of offices from the API
        axios.get('http://localhost:3004/api/v1/search/offices/findAll')
            .then(response => {
                setOffices(response.data.content);
            })
            .catch(error => {
                console.error('Error fetching offices:', error);
            });
    }, []);

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

    const handleSubmit = (event) => {
        event.preventDefault();
        // Gather form data
        const formData = {
            pickupOffice: event.target['pickup-office'].selectedOptions[0].getAttribute('data-name'),
            dropoffOffice: event.target['dropoff-office'].selectedOptions[0].getAttribute('data-name'),
            pickupDate: event.target['pickup-date'].value,
            pickupTime: event.target['pickup-time'].value,
            dropoffDate: event.target['dropoff-date'].value,
            dropoffTime: event.target['dropoff-time'].value,
            car_location: event.target['pickup-office'].selectedOptions[0].getAttribute('data-city')
        };
        // Redirect to /make-reservation page with form data
        // Assuming you are using React Router
        window.location.href = `/make-reservation?${new URLSearchParams(formData).toString()}`;
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
                <div className="title">
                    <h1>Rent Car</h1>
                </div>
                <div className="form">
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="pickup-office">Pick Up Office</label>
                            <select className="input" name="pickup-office">
                                {offices.map(office => (
                                    <option key={office.id} value={office.id} data-city={office.city} data-name={office.name}>{office.name}</option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="dropoff-office">Dropoff Office</label>
                            <select className="input" name="dropoff-office">
                                {offices.map(office => (
                                    <option key={office.id} value={office.id} data-city={office.city} data-name={office.name}>{office.name}</option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="pickup-date">Pick Up Date</label>
                            <input 
                                required
                                className="input"
                                type="date" 
                                id="pickup-date" 
                                name="pickup-date" 
                                onChange={(e) => setPickupDate(e.target.value)} 
                            />
                            <input 
                                className="input"
                                type="time" 
                                id="pickup-time" 
                                name="pickup-time" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="dropoff-date">Dropoff Date</label>
                            <input
                                required 
                                className="input"
                                type="date" 
                                id="dropoff-date" 
                                name="dropoff-date" 
                                onChange={(e) => setDropoffDate(e.target.value)} 
                            />
                            <input 
                                className="input"
                                type="time" 
                                id="dropoff-time" 
                                name="dropoff-time" />
                        </div>
                        <button type="submit">
                            RENT FOR {dayCount > 0 ? dayCount : 'X'} DAY(S)
                        </button>
                    </form>
                </div>
            </div>
        </main>
        </div>
    );
};

export default WelcomePage;
