import React, {useState} from 'react';

import { Box, Typography, Container, TextField, Button} from '@mui/material';

function UserRegistration() {

    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [creditCard, setCreditCard] = useState('');
    const [address, setAddress] = useState('');

    const handleSubmit = () => {
        // Implement the logic to submit the data to the backend
        const userData = {
            email,
            name,
            creditCard,
            address
        };
        console.log('User Data:', userData);
        // You can use fetch or axios to send this data to the backend
    };

    return (
        <Container>
            <Box sx={{ pt: 0, pl: 3 }}>
            <Typography variant="h2" component="h1" gutterBottom align="center">
                    New User Registration
                </Typography>
                <TextField
                    label="Email"
                    variant="outlined"
                    fullWidth
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    sx={{ mb: 2 }}
                />
                <TextField
                    label="Name"
                    variant="outlined"
                    fullWidth
                    required
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    sx={{ mb: 2 }}
                />
                <TextField
                    label="Credit Card"
                    variant="outlined"
                    fullWidth
                    required
                    value={creditCard}
                    onChange={(e) => setCreditCard(e.target.value)}
                    sx={{ mb: 2 }}
                />
                <TextField
                    label="Address"
                    variant="outlined"
                    fullWidth
                    required
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    sx={{ mb: 2 }}
                />
                <Button variant="contained" color="primary" onClick={handleSubmit}>
                    Submit
                </Button>
            </Box>
      </Container>
    );
}

export default UserRegistration;