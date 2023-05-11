import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Login from './Login';
import Register from './Register';
import Chat from './Chat';
import {BrowserRouter, Route, Routes, Switch} from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Login/>} />
            <Route path="/Register" element={<Register/>} />
            <Route path="/Chat" element={<Chat/>} />
        </Routes>
    </BrowserRouter>
);
