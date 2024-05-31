import React from "react";
import { Link } from "react-router-dom";
import { FiPower, FiEdit, FiTrash2 } from "react-icons/fi";
import "./styles.css";

export default function Book() {
    return (
        <div className="book-container">
            <header>
                <span>
                    Welcome, <strong>Anthony</strong>!
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
                <li>
                    <strong>Title:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Author:</strong>
                    <p>Nigel Poulton</p>
                    <strong>Price:</strong>
                    <p>R$ 47,90</p>
                    <strong>Release Date:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#3D37E6" />
                    </button>
                    <button type="button">
                        <FiTrash2 size={20} color="#3D37E6" />
                    </button>
                </li>
                <li>
                    <strong>Title:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Author:</strong>
                    <p>Nigel Poulton</p>
                    <strong>Price:</strong>
                    <p>R$ 47,90</p>
                    <strong>Release Date:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#3D37E6" />
                    </button>
                    <button type="button">
                        <FiTrash2 size={20} color="#3D37E6" />
                    </button>
                </li>
                <li>
                    <strong>Title:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Author:</strong>
                    <p>Nigel Poulton</p>
                    <strong>Price:</strong>
                    <p>R$ 47,90</p>
                    <strong>Release Date:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#3D37E6" />
                    </button>
                    <button type="button">
                        <FiTrash2 size={20} color="#3D37E6" />
                    </button>
                </li>
                <li>
                    <strong>Title:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Author:</strong>
                    <p>Nigel Poulton</p>
                    <strong>Price:</strong>
                    <p>R$ 47,90</p>
                    <strong>Release Date:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#3D37E6" />
                    </button>
                    <button type="button">
                        <FiTrash2 size={20} color="#3D37E6" />
                    </button>
                </li>
                <li>
                    <strong>Title:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Author:</strong>
                    <p>Nigel Poulton</p>
                    <strong>Price:</strong>
                    <p>R$ 47,90</p>
                    <strong>Release Date:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#3D37E6" />
                    </button>
                    <button type="button">
                        <FiTrash2 size={20} color="#3D37E6" />
                    </button>
                </li>
            </ul>
        </div>
    );
}
