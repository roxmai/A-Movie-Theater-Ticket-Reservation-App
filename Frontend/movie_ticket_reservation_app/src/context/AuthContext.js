// src/context/AuthContext.js
import React, { createContext, useState } from 'react';
import { decodeToken } from 'react-jwt';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState({
        token: localStorage.getItem('token'),
        user: localStorage.getItem('token') ? decodeToken(localStorage.getItem('token')) : null
    });

    const login = (token) => {
        localStorage.setItem('token', token);
        setAuth({
            token,
            user: decodeToken(token)
        });
    };

    const logout = () => {
        localStorage.removeItem('token');
        setAuth({
            token: null,
            user: null
        });
    };

    return (
        <AuthContext.Provider value={{ auth, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};