import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiPower, FiEdit, FiTrash2 } from "react-icons/fi";

import "./styles.css";
import api from "../../services/api";

export default function Book() {
    const navigate = useNavigate();
    const [books, setBooks] = useState([]);
    const username = localStorage.getItem("username");
    const accessToken = localStorage.getItem("accessToken");

    useEffect(() => {
        api.get("/books", { headers: { Authorization: `Bearer ${accessToken}` } }).then((response) => {
            setBooks(response.data._embedded.bookDTOList);
        });
    });

    return (
        <div className="book-container">
            <header>
                <span>
                    Welcome, <strong>{username}</strong>!
                </span>
                <Link className="button" to="/books/new">
                    Add new book
                </Link>
                <button type="button">
                    <FiPower size={18} color="#3D37E6" />
                </button>
            </header>

            <h1>Registered Books</h1>
            <ul>
                {books.map((book) => {
                    return (
                        <li>
                            <strong>Title:</strong>
                            <p>{book.title}</p>
                            <strong>Author:</strong>
                            <p>{book.author}</p>
                            <strong>Price:</strong>
                            <p>{Intl.NumberFormat("pt-BR", { style: "currency", currency: "brl" }).format(book.price)}</p>
                            <strong>Release Date:</strong>
                            <p>{Intl.DateTimeFormat("pt-BR").format(new Date(book.launchDate))}</p>

                            <button type="button">
                                <FiEdit size={20} color="#3D37E6" />
                            </button>
                            <button type="button">
                                <FiTrash2 size={20} color="#3D37E6" />
                            </button>
                        </li>
                    );
                })}
            </ul>
        </div>
    );
}
