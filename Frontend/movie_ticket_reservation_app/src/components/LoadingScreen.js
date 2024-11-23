import { Box, CircularProgress, Typography } from '@mui/material';
function LoadingScreen () {
    return (
        <Box sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            mt: 30,
        }}>
            <CircularProgress />
            <Typography variant='body2'>
                loading
            </Typography>
        </Box>)
}

export default LoadingScreen;