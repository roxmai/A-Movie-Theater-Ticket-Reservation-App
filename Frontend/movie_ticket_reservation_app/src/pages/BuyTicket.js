import React from 'react';
import { Box, Typography, Container } from '@mui/material';
import { Link } from 'react-router-dom';
import MovieBrowser from '../components/MovieBrowser';
import TicketSelector from '../components/TicketSelector';

function BuyTickets() {
    return (
      <TicketSelector />
    );
}

export default BuyTickets;