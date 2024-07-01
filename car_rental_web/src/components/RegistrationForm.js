import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Form.css'; // Make sure to import the CSS
import './Header.css';

const RegistrationForm = () => {
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:3001/api/v1/auth/register', {
                "@class": "registrationRequest",
                firstname,
                lastname,
                email,
                password,
            });
            setMessage('Registration successful. Please check your email to confirm your registration.');
            navigate('/login');
        } catch (error) {
            setError('Registration failed. Please try again.');
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
                        <h1> Register </h1>
                    </div>
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                    {message && <p style={{ color: 'green' }}>{message}</p>}
                    <div className="form">
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="firstname">First Name:</label>
                                <input
                                    className="input"
                                    type="text"
                                    id="firstname"
                                    value={firstname}
                                    onChange={(e) => setFirstname(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="lastname">Last Name:</label>
                                <input
                                    className="input"
                                    type="text"
                                    id="lastname"
                                    value={lastname}
                                    onChange={(e) => setLastname(e.target.value)}
                                    required
                                />
                            </div>
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
                            <button type="submit">Register</button>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default RegistrationForm;
