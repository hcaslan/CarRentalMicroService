import React from 'react';
import { useLocation } from 'react-router-dom';

const RentalError = () => {
    const location = useLocation();
    const message = location.state?.message || 'An error occurred.';

    return (
        <div>
            <h1>Error</h1>
            <p>{message}</p>
        </div>
    );
};

export default RentalError;
