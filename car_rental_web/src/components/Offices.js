import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import './Offices.css';

const OfficeListPage = () => {
    const [offices, setOffices] = useState([]);
    const [page, setPage] = useState(0);
    const [size] = useState(20);
    const [totalPages, setTotalPages] = useState(0);

    // State for filters
    const [city, setCity] = useState(null);
    const [officeType, setOfficeType] = useState(null);

    const fetchOffices = useCallback(() => {
        axios.get('http://localhost:3004/api/v1/search/offices/filter', {
            params: { page, size, city, officeType }
        })
            .then(response => {
                console.log('API response:', response.data);
                setOffices(response.data.content);
                setTotalPages(response.data.totalPages);
            })
            .catch(error => {
                console.error('There was an error fetching the office data!', error);
            });
    }, [page, size, city, officeType]);

    useEffect(() => {
        fetchOffices();
    }, [fetchOffices]);

    const handleCityChange = (e) => {
        setCity(e.target.value === 'null' ? null : e.target.value);
    };

    const handleOfficeTypeChange = (e) => {
        setOfficeType(e.target.value === 'null' ? null : e.target.value);
    };

    return (
        <div className="office-list-page">
            <header className="header">
                <div className="logo">
                    <a href="/welcome"><img src="/logo.png" alt="Logo" /></a>
                    <a href="/welcome">HCA</a>
                </div>
                <div className="filter-selector">
                    <form>
                        <div className="filter">
                            <label htmlFor="filter1">City:</label>
                            <select id="filter1" name="filter1" value={city} onChange={handleCityChange}>
                                <option value="null">SELECT</option>
                                <option value="adana">Adana</option>
                                <option value="ankara">Ankara</option>
                                <option value="antalya">Antalya</option>
                                <option value="aydin">Aydin</option>
                                <option value="bursa">Bursa</option>
                                <option value="eskisehir">Eskisehir</option>
                                <option value="istanbul">Istanbul</option>
                                <option value="izmir">İzmir</option>
                                <option value="muğla">Muğla</option>
                            </select>
                        </div>
                        <div className="filter">
                            <label htmlFor="filter2">Office Type:</label>
                            <select id="filter2" name="filter2" value={officeType} onChange={handleOfficeTypeChange}>
                                <option value="null">SELECT</option>
                                <option value="city">CITY</option>
                                <option value="airport">AIRPORT</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div className="user-actions">
                    <a href="/login" className="login">Login</a>
                    <a href="/register" className="register">Register</a>
                </div>
            </header>
            <main className="main-content">
                <h1>Available Offices</h1>
                <div className="office-list">
                    {offices.length === 0 && <p>No offices available.</p>}
                    {offices.map(office => (
                        <div key={office.id} className="office-card">
                            <div className="office-details">
                                <div className="office-name">
                                    <h2>{office.name}</h2>
                                </div>
                                <div className="office-properties">
                                    <p><strong>City:</strong> {office.city}</p>
                                    <p><strong>Type:</strong> {office.officeType}</p>
                                    <p><strong>Address:</strong> {office.address}</p>
                                    <p><strong>Phone:</strong> {office.phone}</p>
                                </div>
                                <button>Contact Now</button>
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

export default OfficeListPage;
