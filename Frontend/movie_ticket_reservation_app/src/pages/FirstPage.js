import React from 'react';
import { Box, Typography, Container } from '@mui/material';

function FirstPage() {
    return (
        <Container>
        <Box sx={{ pt: 0, pl: 3 }}> {/* Padding top is 0, padding left is 3 * 8px = 24px */}
          <Typography variant="h2" component="h1" gutterBottom align="center">
            Movies
          </Typography>
          <Typography variant="body1" sx={{ textAlign: 'justify' }}>
            This is the content for the movies page.
          </Typography>
          {/* More content can be added here */}
        </Box>
      </Container>
    );
}

export default FirstPage;