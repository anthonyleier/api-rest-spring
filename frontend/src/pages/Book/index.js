import React from "react";
import { Link } from "react-router-dom";
import { FiPower } from "react-icons/fi";
import "./styles.css";

export default function Book() {
    return (
        <div className="book-container">
            <header>
                <span>
                    Welcome, <strong>Anthony</strong>!
                </span>
                <Link className="button" to="book/new">
                    Add new book
                </Link>
                <button type="button">
                    <FiPower size={18} color="#3D37E6" />
                </button>
            </header>

            <h1>Registered Books</h1>
            <ul></ul>
        </div>
    );
}
