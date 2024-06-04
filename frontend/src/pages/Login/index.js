import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import "./styles.css";
import api from "../../services/api";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    async function login(event) {
        event.preventDefault();

        const data = {
            username,
            password,
        };

        try {
            const response = await api.post("/auth/signin", data);
            localStorage.setItem("username", username);
            localStorage.setItem("accessToken", response.data.accessToken);
            navigate('/books')
        } catch (error) {
            alert("Login failed, try again");
        }
    }

    return (
        <div className="login-container">
            <section className="form">
                <form onSubmit={login}>
                    <h1>Access your account</h1>
                    <input placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
                    <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
                    <button type="submit" className="button">
                        Login
                    </button>
                </form>
            </section>
        </div>
    );
}
