import React, { useState } from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Login from './Login';
import Register from './Register';
import Chat from './Chat';
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
    const [username, setUsername] = useState("");

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login setUsernameNew={setUsername} />} />
                <Route path="/Register" element={<Register />} />
                <Route path="/Chat" element={<Chat username={username} />} />
            </Routes>
        </BrowserRouter>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
