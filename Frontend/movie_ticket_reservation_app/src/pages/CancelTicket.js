import React,{ lazy, useState } from 'react';
import { cancelTicket } from '../api/Services';

import { Box, Typography, Container, TextField, Button,Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import InfoIcon from '@mui/icons-material/Info';

function CancelTicketPage() {

    const [ticketNumber, setTicketNumber] = useState('');
    const [successDialogOpen, setSuccessDialogOpen] = useState(false);
    const [errorDialogOpen, setErrorDialogOpen] = useState(false);
    const [response, setResponse] = useState(null);
    const [loading, setLoading] = useState(false);

    const issue = async () => {
        // Implement the refund logic here
        try {
            setLoading(true);
            const data = await cancelTicket(ticketNumber);
            
            if (data?.error) {
                setErrorDialogOpen(true);
            } else if (data?.success) {
                //const dataCredit = await issueRefund(ticketNumber);
                setSuccessDialogOpen(true);
            }
            setResponse(data)
        } catch (error) {
            console.error('aa',error);
        } finally {
            setLoading(false);
        }
        console.log(`Refund issued for ticket number: ${ticketNumber}`);

    };

    const handleSuccessDialogClose = () => {
        setSuccessDialogOpen(false);
    };

    const handleErrorDialogClose = () => {
        setErrorDialogOpen(false);
    };

    return (
        <Container>
            <Box sx={{ mt: 4 }}>
                <Typography variant="h2" component="h1" gutterBottom align="center">
                    Cancel Ticket
                </Typography>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 , gap: 1}}>
                    <InfoIcon color="warning" />
                    <Typography variant="body1">
                        Ticket cannot be cancelled within 72 hours before the showtime
                    </Typography>
                </Box>
                <TextField
                    label="Ticket Number"
                    variant="outlined"
                    fullWidth
                    onChange={(e) => setTicketNumber(e.target.value)}
                    sx={{ mb: 2, mt: 1 }}
                />
                <Button variant="contained" color="primary" onClick={issue}>
                    cancel Ticket
                </Button>
            </Box>
            <Dialog open={successDialogOpen} onClose={handleSuccessDialogClose}>
                <DialogTitle>Cancellation success</DialogTitle>
                <DialogContent>
                    {
                        loading ? <Typography>Processing...</Typography> : <DialogContentText>
                        {response?.message}
                        {response?.emailSent && <Typography>An email notification has been sent to you.</Typography>}
                    </DialogContentText>
                    }
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleSuccessDialogClose} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
            <Dialog open={errorDialogOpen} onClose={handleErrorDialogClose}>
                <DialogTitle>Cancellation failed</DialogTitle>
                <DialogContent>
                    {
                        loading ? <Typography>Processing...</Typography> : <DialogContentText>
                        {response?.message}
                    </DialogContentText>
                    }
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleErrorDialogClose} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}

export default CancelTicketPage;