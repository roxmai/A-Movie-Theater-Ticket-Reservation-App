import React, { useState } from 'react';
import { AppBar, Toolbar, Typography, Button, Box, Menu, MenuItem, Dialog, DialogTitle, DialogContent, DialogContentText, TextField, DialogActions } from '@mui/material';
import { Link } from 'react-router-dom';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { membershipPayment, login } from '../api/Services';

function NavBar() {
    const [anchorEl, setAnchorEl] = useState(null);
    const [open, setOpen] = useState(false);
    const [loggedIn, setLoggedIn] = useState(false);
    const [userName, setUserName] = useState('User');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorDialogOpen, setErrorDialogOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleErrorDialogClose = () => {
        setErrorDialogOpen(false);
    };

    const handleLogin = async () => {
        const response = await login(email, password);

        if (response?.success) {
            setLoggedIn(true);
            setEmail(email);
            setUserName(response.name);
            setOpen(false);
        } else if (response?.expired) {
            alert(response.error);
        } else if (response?.wrong) {
            setErrorMessage(response.message);
            setErrorDialogOpen(true);
        }
    };

    const handleLogout = () => {
        setLoggedIn(false);
        setEmail('');
        handleMenuClose();
    };

    const handleSubscriptionRenew = async () => {
        try {
            const response = await membershipPayment(email, 'credit');
            console.log('Membership payment successful:', response);
            alert('Membership renewed successfully.');
        } catch (error) {
            console.error('Error renewing membership:', error);
            alert('Failed to renew membership. Please try again.');
        }
        handleMenuClose();
    };

    return (
        <AppBar position="static">
            <Toolbar>
                <Box sx={{ flexGrow: 1 }}>
                    <Button color="inherit" component={Link} to="/BookMovie">Book Movie</Button>
                    <Button color="inherit" component={Link} to="/CancelTicket">Cancel Ticket</Button>
                    <Button color="inherit" component={Link} to="/UserRegistration">User Registration</Button>
                </Box>
                <Box sx={{ flexGrow: 0 }}>
                    {loggedIn ? (
                        <>
                            <Button
                                color="inherit"
                                endIcon={<ArrowDropDownIcon />}
                                onClick={handleMenuOpen}
                            >
                                {userName}
                            </Button>
                            <Menu
                                anchorEl={anchorEl}
                                open={Boolean(anchorEl)}
                                onClose={handleMenuClose}
                            >
                                <MenuItem onClick={handleSubscriptionRenew}>Subscription Renew</MenuItem>
                                <MenuItem onClick={handleLogout}>Logout</MenuItem>
                            </Menu>
                        </>
                    ) : (
                        <Button color="inherit" onClick={handleClickOpen}>Login</Button>
                    )}
                </Box>
            </Toolbar>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Login</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Please enter your login details.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="email"
                        label="Email Address"
                        type="email"
                        fullWidth
                        variant="standard"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <TextField
                        margin="dense"
                        id="password"
                        label="Password"
                        type="password"
                        fullWidth
                        variant="standard"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleLogin} color="primary">
                        Login
                    </Button>
                </DialogActions>
            </Dialog>
            <Dialog open={errorDialogOpen} onClose={handleErrorDialogClose}>
                <DialogTitle>Error</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        {errorMessage}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleErrorDialogClose} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </AppBar>
    );
}

export default NavBar;