import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import "./global.css";
import Login from "./pages/Login";
import Book from "./pages/Book";

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" Component={Login} />
                <Route path="/books" Component={Book} />
            </Routes>
        </Router>
    );
};

export default App;
