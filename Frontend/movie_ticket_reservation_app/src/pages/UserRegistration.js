import React, { useState } from 'react';
import { Box, Typography, Container, TextField, Button, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, RadioGroup, FormControlLabel, Radio } from '@mui/material';
import { createRegisteredUser } from '../api/Services';

function UserRegistration() {
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [creditCard, setCreditCard] = useState('');
    const [address, setAddress] = useState('');
    const [dialogOpen, setDialogOpen] = useState(false);
    const [confirmationDialogOpen, setConfirmationDialogOpen] = useState(false);

    const [expireMonth, setExpireMonth] = useState('');
    const [expireYear, setExpireYear] = useState('');
    const [cvv, setCvv] = useState('');
    const [creditcard, setNameOnCard] = useState('');
    const [method, setCardType] = useState('credit');

    const handleSubmit = () => {
        setConfirmationDialogOpen(true);
    };

    const handleDialogClose = () => {
        setConfirmationDialogOpen(false);
    };

    const handleRegistration = async () => {
        const registrationData = {
            email,
            name,
            password,
            address,
            creditcard
        };
        console.log('Registration Data:', registrationData);

        try {
            const response = await createRegisteredUser(registrationData);
            console.log(response);
            setDialogOpen(true);
            setConfirmationDialogOpen(false);
        } catch (error) {
            console.error('Error processing registration:', error);
        }
    };

    const handlePaymentDialogClose = () => {
        setDialogOpen(false);
    };

    return (
        <Container>
            <Box sx={{ pt: 0, pl: 3 }}>
                <Typography variant="h2" component="h1" gutterBottom align="center">
                    User Registration
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
                    label="Password"
                    variant="outlined"
                    fullWidth
                    required
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
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
                <Typography variant="body2" color="textSecondary" sx={{ mb: 2 }}>
                    Paying $15 membership fee upon registration
                </Typography>
                <Button variant="contained" color="primary" onClick={handleSubmit}>
                    Register
                </Button>
            </Box>
            <Dialog open={confirmationDialogOpen} onClose={handleDialogClose}>
                <DialogTitle>Enter Payment Details</DialogTitle>
                <DialogContent>
                    <RadioGroup
                        aria-label="cardType"
                        name="cardType"
                        value={method}
                        onChange={(e) => setCardType(e.target.value)}
                        row
                    >
                        <FormControlLabel value="credit" control={<Radio />} label="Credit" />
                        <FormControlLabel value="debit" control={<Radio />} label="Debit" />
                    </RadioGroup>
                    <TextField
                        label="Card Number"
                        variant="outlined"
                        fullWidth
                        required
                        value={creditCard}
                        onChange={(e) => setCreditCard(e.target.value)}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        label="Expire Month"
                        variant="outlined"
                        fullWidth
                        required
                        value={expireMonth}
                        onChange={(e) => setExpireMonth(e.target.value)}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        label="Expire Year"
                        variant="outlined"
                        fullWidth
                        required
                        value={expireYear}
                        onChange={(e) => setExpireYear(e.target.value)}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        label="CVV"
                        variant="outlined"
                        fullWidth
                        required
                        value={cvv}
                        onChange={(e) => setCvv(e.target.value)}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        label="Name on Card"
                        variant="outlined"
                        fullWidth
                        required
                        value={creditcard}
                        onChange={(e) => setNameOnCard(e.target.value)}
                        sx={{ mb: 2 }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleRegistration} color="primary">
                        Confirm Info
                    </Button>
                </DialogActions>
            </Dialog>
            <Dialog open={dialogOpen} onClose={handlePaymentDialogClose}>
                <DialogTitle>Registration Successful</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Payment successful and account registered.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handlePaymentDialogClose} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}

export default UserRegistration;