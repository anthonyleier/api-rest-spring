import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import "./global.css";
import Login from "./pages/Login";
import Book from "./pages/Book";
import NewBook from "./pages/NewBook";

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" Component={Login} />
                <Route path="/books" Component={Book} />
                <Route path="/books/new/:bookID" Component={NewBook} />
            </Routes>
        </Router>
    );
};

export default App;
