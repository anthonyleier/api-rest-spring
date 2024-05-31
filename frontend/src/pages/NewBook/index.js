import React from "react";
import { Link } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";
import "./styles.css";

export default function NewBook() {
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
                <form>
                    <input placeholder="Title" />
                    <input placeholder="Author" />
                    <input placeholder="Price" />
                    <input type="date" />
                    <button className="button" type="submit">
                        Add
                    </button>
                </form>
            </div>
        </div>
    );
}
