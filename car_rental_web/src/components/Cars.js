import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import './Cars.css';
import './Header.css';

const CarListPage = () => {
    const [cars, setCars] = useState([]);
    const [page, setPage] = useState(0);
    const [size] = useState(20);
    const [totalPages, setTotalPages] = useState(0);

    // State for filters
    const [category, setCategory] = useState(null);
    const [gearType, setGearType] = useState(null);
    const [fuelType, setFuelType] = useState(null);
    const [minDaily, setMinDaily] = useState('');
    const [maxDaily, setMaxDaily] = useState('');

    const fetchCars = useCallback(() => {
        axios.get('http://localhost:3004/api/v1/search/cars/filter', {
            params: { page, size, category, gearType, fuelType, minDaily, maxDaily }
        })
            .then(response => {
                console.log('API response:', response.data);
                setCars(response.data.content);
                setTotalPages(response.data.totalPages);
            })
            .catch(error => {
                console.error('There was an error fetching the car data!', error);
            });
    }, [page, size, category, gearType, fuelType, minDaily, maxDaily]);

    useEffect(() => {
        fetchCars();
    }, [fetchCars]);

    const handleCategoryChange = (e) => {
        setCategory(e.target.value === 'null' ? null : e.target.value);
    };

    const handleGearTypeChange = (e) => {
        setGearType(e.target.value === 'null' ? null : e.target.value);
    };

    const handleFuelTypeChange = (e) => {
        setFuelType(e.target.value === 'null' ? null : e.target.value);
    };
    const handleMinDaily = (e) => {
        setMinDaily(e.target.value === '' ? '' : Number(e.target.value));
    };
    const handleMaxDaily = (e) => {
        setMaxDaily(e.target.value === '' ? '' : Number(e.target.value));
    };

    return (
        <div className="car-list-page">
            <header className="header">
                <div className="logo">
                    <a href="/welcome"><img src="/logo.png" alt="Logo" /></a>
                    <a href="/welcome">HCA</a>
                </div>
                <div className="filter-selector">
                    <form>
                        <div className="filter">
                            <label htmlFor="filter1">Category:</label>
                            <select id="filter1" name="filter1" value={category} onChange={handleCategoryChange}>
                                <option value="null">SELECT</option>
                                <option value="small">SMALL</option>
                                <option value="medium">MEDIUM</option>
                                <option value="large">LARGE</option>
                                <option value="estate">ESTATE</option>
                                <option value="premium">PREMIUM</option>
                                <option value="carrier">CARRIER</option>
                                <option value="suv">SUV</option>
                            </select>
                        </div>
                        <div className="filter">
                            <label htmlFor="filter2">Fuel Type:</label>
                            <select id="filter2" name="filter2" value={fuelType} onChange={handleFuelTypeChange}>
                                <option value="null">SELECT</option>
                                <option value="petrol">PETROL</option>
                                <option value="diesel">DIESEL</option>
                                <option value="electric">ELECTRIC</option>
                                <option value="hybrid">HYBRID</option>
                                <option value="gas">GAS</option>
                            </select>
                        </div>
                        <div className="filter">
                            <label htmlFor="filter3">Gear Type:</label>
                            <select id="filter3" name="filter3" value={gearType} onChange={handleGearTypeChange}>
                                <option value="null">SELECT</option>
                                <option value="automatic">AUTOMATIC</option>
                                <option value="manuel">MANUAL</option>
                            </select>
                        </div>
                        <div className="filter daily-price-filter">
                            <label htmlFor="minDaily">Daily Price:</label>
                            <div className="daily-price-inputs">
                                <input 
                                    type="number"
                                    step="any"
                                    value={minDaily}
                                    onChange={handleMinDaily}
                                    min="5"
                                    id="minDaily"
                                    placeholder="Min"
                                />
                                <input 
                                    type="number"
                                    step="any"
                                    value={maxDaily}
                                    onChange={handleMaxDaily}
                                    min="5"
                                    id="maxDaily"
                                    placeholder="Max"
                                />
                            </div>
                        </div>
                    </form>
                </div>
                <div className="user-actions">
                    <a href="/login" className="login">Login</a>
                    <a href="/register" className="register">Register</a>
                </div>
            </header>
            <main className="main-content">
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
                                </div>
                                <button>Rent Now</button>
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

export default CarListPage;
