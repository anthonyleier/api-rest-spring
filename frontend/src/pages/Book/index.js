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
        api.get("/books", { headers: { Authorization: `Bearer ${accessToken}` }, params: { page: 1, size: 4, direction: "asc" } }).then((response) => {
            setBooks(response.data._embedded.bookDTOList);
        });
    });

    function logout() {
        localStorage.clear();
        navigate("/");
    }

    function editBook(id) {
        try {
            navigate(`/books/new/${id}`);
        } catch (error) {
            alert("Edit failed, try again");
        }
    }

    async function deleteBook(id) {
        try {
            await api.delete(`/books/${id}`, { headers: { Authorization: `Bearer ${accessToken}` } });
            setBooks(books.filter((book) => book.id !== id));
        } catch (error) {
            alert("Delete failed, try again");
        }
    }

    return (
        <div className="book-container">
            <header>
                <span>
                    Welcome, <strong>{username}</strong>!
                </span>
                <Link className="button" to="/books/new/0">
                    Add new book
                </Link>
                <button type="button" onClick={logout}>
                    <FiPower size={18} color="#3D37E6" />
                </button>
            </header>

            <h1>Registered Books</h1>
            <ul>
                {books.map((book) => {
                    return (
                        <li key={book.id}>
                            <strong>Title:</strong>
                            <p>{book.title}</p>
                            <strong>Author:</strong>
                            <p>{book.author}</p>
                            <strong>Price:</strong>
                            <p>{Intl.NumberFormat("pt-BR", { style: "currency", currency: "brl" }).format(book.price)}</p>
                            <strong>Launch Date:</strong>
                            <p>{Intl.DateTimeFormat("pt-BR").format(new Date(book.launchDate))}</p>

                            <button type="button" onClick={() => editBook(book.id)}>
                                <FiEdit size={20} color="#3D37E6" />
                            </button>
                            <button type="button" onClick={() => deleteBook(book.id)}>
                                <FiTrash2 size={20} color="#3D37E6" />
                            </button>
                        </li>
                    );
                })}
            </ul>
        </div>
    );
}
