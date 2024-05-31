import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import "./global.css";
import Login from "./pages/Login";

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" Component={Login} />
            </Routes>
        </Router>
    );
};

export default App;
