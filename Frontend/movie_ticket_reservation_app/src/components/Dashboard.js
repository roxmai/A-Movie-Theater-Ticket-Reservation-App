import React, { useEffect, useState } from 'react';
import axiosInstance from '../utils/axiosInstance';

// Create a component that fetches protected data from the backend.
const Dashboard = () => {
    const [data, setData] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosInstance.get('/test/user');
                setData(response.data);
            } catch (err) {
                setData('Error fetching data');
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <h2>Dashboard</h2>
            <p>{data}</p>
        </div>
    );
};

export default Dashboard;