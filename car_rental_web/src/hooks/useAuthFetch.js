import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import {useNavigate} from 'react-router-dom';

const useAuthFetch = (token) => {
    const navigate = useNavigate();

    return async (url, setData, setLoading) => {
        try {
            if (token) {
                const decodedToken = jwtDecode(token);
                const currentTime = Date.now() / 1000;

                if (decodedToken.exp > currentTime) {
                    const response = await axios.get(url, {
                        headers: {Authorization: `Bearer ${token}`}
                    });
                    if (response.status === 200) {
                        setData(response.data);
                    } else {
                        console.error('Error fetching data:', response.data);
                    }
                } else {
                    console.log('Token is expired, redirecting to login...');
                    localStorage.removeItem('token');
                    navigate(`/login?returnUrl=${encodeURIComponent(window.location.pathname)}&message=Your session has expired, please log in again.`);
                }
            } else {
                console.log('No token found, redirecting to login...');
                navigate(`/login?returnUrl=${encodeURIComponent(window.location.pathname)}&message=Please log in to view this page.`);
            }
        } catch (error) {
            console.error('Network error:', error);
            navigate('/error', {state: {message: error.message || 'Network error.'}});
        } finally {
            setLoading(false);
        }
    };
};

export default useAuthFetch;
