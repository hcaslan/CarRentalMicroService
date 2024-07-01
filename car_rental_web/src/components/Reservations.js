import React, { useState, useEffect } from 'react';
import './Header.css';
import './Form.css';
import useAuthFetch from '../hooks/useAuthFetch';

const ReservationsPage = () => {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem('token');
    const fetchWithAuth = useAuthFetch(token);

    useEffect(() => {
        fetchWithAuth('http://localhost:3004/api/v1/reservations', setReservations, setLoading);
    }, [fetchWithAuth]);

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
                    <h1>Your Reservations</h1>
                    {loading ? (
                        <p>Loading your reservations...</p>
                    ) : (
                        <div className="reservations-list">
                            {reservations.length > 0 ? (
                                reservations.map(reservation => (
                                    <div key={reservation.id} className="reservation-item">
                                        <h2>Reservation #{reservation.id}</h2>
                                        <div className="summary-item">
                                            <span className="label">Pick Up Office:</span>
                                            <span className="value">{reservation.pickupOffice}</span>
                                        </div>
                                        <div className="summary-item">
                                            <span className="label">Drop Off Office:</span>
                                            <span className="value">{reservation.dropoffOffice}</span>
                                        </div>
                                        <div className="summary-item">
                                            <span className="label">Pick Up:</span>
                                            <span className="value">{reservation.pickupDate} - {reservation.pickupTime}</span>
                                        </div>
                                        <div className="summary-item">
                                            <span className="label">Drop Off:</span>
                                            <span className="value">{reservation.dropoffDate} - {reservation.dropoffTime}</span>
                                        </div>
                                        <div className="summary-item">
                                            <span className="label">Car:</span>
                                            <span className="value">{reservation.carName}</span>
                                        </div>
                                        <div className="summary-item">
                                            <span className="label">Location:</span>
                                            <span className="value">{reservation.carLocation}</span>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p>You have no reservations.</p>
                            )}
                        </div>
                    )}
                </div>
            </main>
        </div>
    );
};

export default ReservationsPage;
