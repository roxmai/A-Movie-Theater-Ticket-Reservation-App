import React, { useState } from 'react';
import { AppBar, Toolbar, Typography, Button, Box, Menu, MenuItem, Dialog, DialogTitle, DialogContent, DialogContentText, TextField,DialogActions } from '@mui/material';
import { Link } from 'react-router-dom';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { membershipPayment } from '../api/Services';

function NavBar() {
    const [anchorEl, setAnchorEl] = useState(null);
    const [open, setOpen] = useState(false);
    const [loggedIn, setLoggedIn] = useState(false);
    const [userName, setUserName] = useState('User');

    const [email, setEmail] = useState(''); 

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

    const handleLogin = () => {
        // Implement login logic here
        setLoggedIn(true);
        setEmail(email);
        setUserName('OU');
        setOpen(false);
    };

    const handleLogout = () => {
        // Implement logout logic here
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
                    />
                    <TextField
                        margin="dense"
                        id="password"
                        label="Password"
                        type="password"
                        fullWidth
                        variant="standard"
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
        </AppBar>
    );
}

export default NavBar;