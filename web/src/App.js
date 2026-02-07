import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import RegisterPage from "./pages/RegisterPage";
import LoginPage from "./pages/LoginPage";
import DashboardPage from "./pages/DashboardPage";

function App() {
    const [loggedInUser, setLoggedInUser] = useState("");

    const handleLogout = () => setLoggedInUser("");

    return (
        <Router>
            <nav style={{ marginBottom: "20px" }}>
                <Link to="/register" style={{ marginRight: "10px" }}>Register</Link>
                <Link to="/login" style={{ marginRight: "10px" }}>Login</Link>
                {loggedInUser && <button onClick={handleLogout}>Logout</button>}
            </nav>

            <Routes>
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/login" element={<LoginPage onLogin={setLoggedInUser} />} />
                <Route path="/dashboard" element={<DashboardPage username={loggedInUser} />} />
            </Routes>
        </Router>
    );
}

export default App;
