import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar, IconButton, Button, Menu, MenuItem, Box } from '@mui/material';

function NavBar() {
    return (
        <AppBar position="static">
            <Toolbar>

                <Button color="inherit" component={Link} to="/home">Home</Button>
            </Toolbar>
        </AppBar>
    );

}

export default NavBar;