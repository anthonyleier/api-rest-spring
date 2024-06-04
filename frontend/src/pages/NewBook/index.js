import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

import "./styles.css";
import api from "../../services/api";

export default function NewBook() {
    const [id, setId] = useState(null);
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [price, setPrice] = useState("");
    const [launchDate, setLaunchDate] = useState("");

    const { bookId } = useParams();
    const navigate = useNavigate();
    const accessToken = localStorage.getItem("accessToken");

    useEffect(() => {
        if (bookId === "0") return;
        else loadBook();

        async function loadBook() {
            try {
                const response = await api.get(`/books/${bookId}`, { headers: { Authorization: `Bearer ${accessToken}` } });
                setId(response.data.id);
                setTitle(response.data.title);
                setAuthor(response.data.author);
                setPrice(response.data.price);
                setLaunchDate(response.data.launchDate);
            } catch (error) {
                alert("Erro while loading book, try again");
                navigate("/books");
            }
        }
    }, [bookId, accessToken, navigate]);

    async function createBook(event) {
        event.preventDefault();

        const data = {
            title,
            author,
            price,
            launchDate,
        };

        try {
            await api.post("/books", data, { headers: { Authorization: `Bearer ${accessToken}` } });
            navigate("/books");
        } catch (error) {
            alert("Erro while recording book, try again");
        }
    }

    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <h1>Add new book</h1>
                    <p>Enter the book information and click on 'Add'!</p>

                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#737380"></FiArrowLeft>
                        HOME
                    </Link>
                </section>
                <form onSubmit={createBook}>
                    <input placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} />
                    <input placeholder="Author" value={author} onChange={(e) => setAuthor(e.target.value)} />
                    <input placeholder="Price" value={price} onChange={(e) => setPrice(e.target.value)} />
                    <input type="date" value={launchDate} onChange={(e) => setLaunchDate(e.target.value)} />
                    <button className="button" type="submit">
                        Add
                    </button>
                </form>
            </div>
        </div>
    );
}
