import React, { useEffect, useState } from "react";
import axios from "axios";
import "./DashboardPage.css";


const DashboardPage = ({ username }) => {
    const [user, setUser] = useState(null);
    const [message, setMessage] = useState("");

    useEffect(() => {
        if (!username) {
            setMessage("You must login first");
            return;
        }

        const fetchUser = async () => {
            try {
                const res = await axios.get("http://localhost:8080/api/user/me", { withCredentials: true });
                setUser(res.data);
            } catch (err) {
                if (err.response && typeof err.response.data === "object") {
                    setMessage(JSON.stringify(err.response.data));
                } else {
                    setMessage(err.response?.data || "Server error");
                }
            }
        };

        fetchUser();
    }, [username]);

    if (!username) return <p>{message}</p>;

    return (
        <div className="dashboard-page-container">
            <h2 className="dashboard-page-title">Dashboard</h2>
            {user ? (
                <div>
                    <div className="dashboard-page-user-info">
                        <p>Welcome, {user.dashboardusername}!</p>
                        <p>This is your sample dashboard.</p>
                    </div>
                    <ul className="dashboard-page-list">
                        <li>Profile Info</li>
                        <li>Recent Activity</li>
                        <li>Settings</li>
                    </ul>
                </div>
            ) : (
                <p className="dashboard-page-message">{message || "Loading..."}</p>
            )}
        </div>
    );
};

export default DashboardPage;