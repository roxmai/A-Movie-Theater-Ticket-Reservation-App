import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import CancelTicket from './pages/CancelTicket';
import FirstPage from './pages/FirstPage';


function App() {

  return (
      <Router>
          <NavBar />
          <Routes>
              <Route path="/" element={<FirstPage />} />
          </Routes>
      </Router>
  );
}

export default App;
