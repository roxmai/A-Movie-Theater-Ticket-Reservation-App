import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

//To ensure that only authenticated users can access the Dashboard
const PrivateRoute = ({ children }) => {
  const { auth } = useContext(AuthContext);

  return auth.user ? children : <Navigate to="/" replace />;
};

export default PrivateRoute;