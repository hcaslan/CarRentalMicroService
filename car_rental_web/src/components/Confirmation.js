import React, { useState, useEffect, useCallback } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import './Form.css';
import './Header.css';

const Confirmation = () => {
    const [status, setStatus] = useState(null);
    const location = useLocation();

    const query = new URLSearchParams(location.search);
    const token = query.get('token');

    const confirmRegistration = useCallback(async () => {
        try {
            const response = await axios.post(`http://localhost:3001/api/v1/auth/confirm?token=${token}`);
            if (response.status === 200) {
                setStatus('success');
            } else {
                setStatus('failure');
            }
        } catch (error) {
            setStatus('failure');
        }
    }, [token]);

    useEffect(() => {
        if (status === null) {
            confirmRegistration();
        }
    }, [status, confirmRegistration]);

    return (
        <div className="page-container">
            <header className="header">
                <div className="logo">
                    <a href="/welcome"><img src="/logo.png" alt="Logo" /></a>
                    <a href="/welcome">HCA</a>
                </div>
                <div className="user-actions">
                    <a href="/login" className="login">Login</a>
                    <a href="/register" className="register">Register</a>
                </div>
            </header>
            <main className="main-content">
                <div className="form-container">
                    {status === 'success' && (
                        <>
                            <h2>Activation Successful</h2>
                            <p>Your activation has been successfully completed.</p>
                            <a href="/login">Go to Login</a>
                        </>
                    )}
                    {status === 'failure' && (
                        <>
                            <h2>Activation Failed</h2>
                            <p>There was an issue with your activation. Please try again.</p>
                            <a href="/register">Go to Register</a>
                        </>
                    )}
                    {status === null && <p>Loading...</p>}
                </div>
            </main>
        </div>
    );
};

export default Confirmation;
