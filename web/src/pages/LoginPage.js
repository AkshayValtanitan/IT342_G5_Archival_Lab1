import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./LoginPage.css";

const LoginPage = ({ onLogin }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post("http://localhost:8080/api/auth/login", null, {
                params: { username, password },
                withCredentials: true
            });
            if (res.data === "Login successful") {
                onLogin(username);
                navigate("/dashboard");
            } else {
                setMessage(res.data);
            }
        } catch (err) {
            setMessage(err.response?.data || "Server error");
        }
    };

    return (
        <div className="login-page-container">
            <h2 className="login-page-title">Login</h2>
            <form onSubmit={handleLogin} className="login-page-form">
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                    className="login-page-input"
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    className="login-page-input"
                />
                <button type="submit" className="login-page-button">Login</button>
            </form>
            {message && <div className="login-page-message">{message}</div>}
        </div>
    );
};

export default LoginPage;