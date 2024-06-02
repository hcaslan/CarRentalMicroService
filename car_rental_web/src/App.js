import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import RegistrationForm from './components/RegistrationForm';
import WelcomePage from './components/WelcomePage';
import Confirmation from './components/Confirmation';
import Cars from './components/Cars';
import Offices from './components/Offices';
import MakeReservationPage from './components/MakeReservationPage';
import PaymentInformationPage from './components/PaymentInformationPage';
import './App.css';

function App() {
  return (
      <Router>
        <div className="App">
          <Routes>
            <Route path="/login" element={<LoginForm />} />
            <Route path="/register" element={<RegistrationForm />} />
            <Route path="/welcome" element={<WelcomePage />} />
            <Route path="/confirm" element={<Confirmation />} />
            <Route path="/inventory" element={<Cars />} />
            <Route path="/locations" element={<Offices />} />
            <Route path="/make-reservation" element={<MakeReservationPage />} />
            <Route path="/payment-information" element={<PaymentInformationPage />} />
            <Route path="/" element={<WelcomePage />} />
          </Routes>
        </div>
      </Router>
  );
}


export default App;
