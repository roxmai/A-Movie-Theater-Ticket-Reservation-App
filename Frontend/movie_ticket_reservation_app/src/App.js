import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import CancelTicketPage from './pages/CancelTicket';
import BookMovie from './pages/BookMovie';
import UserRegistration from './pages/UserRegistration';
import BuyTickets from './pages/BuyTicket';
import TicketPaymentPage from './pages/TicketPaymentPage';


function App() {

  return (
      <Router>
          <NavBar />
          <Routes>
              <Route path="/BookMovie" element={<BookMovie />} />
              <Route path="/CancelTicket" element={<CancelTicketPage />} />
              <Route path="/UserRegistration" element={<UserRegistration />} />
              <Route path="/movie/:id" element={<BuyTickets />} />
              <Route path="/Payment" element={<TicketPaymentPage />} />
          </Routes>
      </Router>
  );
}

export default App;
