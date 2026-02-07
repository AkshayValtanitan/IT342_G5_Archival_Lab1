import React, { useState } from "react";
import axios from "axios";
import "./RegisterPage.css";

const RegisterPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post(
                "http://localhost:8080/api/auth/register",
                null,
                { params: { username, password }, withCredentials: true }
            );
            setMessage(res.data);
        } catch (err) {
            setMessage(err.response?.data || "Server error");
        }
    };

    return (
        <div className="register-container">
            <h2 className="register-h2">Register</h2>
            <form onSubmit={handleRegister} className="register-form">
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                    className="register-input"
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    className="register-input"
                />
                <button type="submit" className="register-button">
                    Register
                </button>
            </form>
            {message && <p className="register-message">{message}</p>}
        </div>
    );
};

export default RegisterPage;