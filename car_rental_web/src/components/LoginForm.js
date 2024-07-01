import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import './Header.css';
import './Form.css';

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [notification, setNotification] = useState('');
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const query = new URLSearchParams(location.search);
        const message = query.get('message');
        if (message) {
            setNotification(message);
        }
    }, [location.search]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:3001/api/v1/auth/login', { email, password });
            localStorage.setItem('token', response.data);

            const reservationData = JSON.parse(localStorage.getItem('reservationData'));
            if (reservationData) {
                localStorage.removeItem('reservationData');
                const returnUrl = location.search.includes('returnUrl')
                    ? new URLSearchParams(location.search).get('returnUrl')
                    : '/payment-information';
                navigate(`${returnUrl}?${new URLSearchParams(reservationData).toString()}`);
            } else {
                navigate('/welcome');
            }
        } catch (error) {
            setError('Invalid username or password.');
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
                    <div className="title">
                        <h1> Login </h1>
                    </div>
                    {notification && <p style={{ color: 'blue' }}>{notification}</p>}
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                    <div className="form">
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="email">Email:</label>
                                <input
                                    className="input"
                                    type="email"
                                    id="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password:</label>
                                <input
                                    className="input"
                                    type="password"
                                    id="password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                            </div>
                            <button type="submit">Login</button>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default LoginForm;
