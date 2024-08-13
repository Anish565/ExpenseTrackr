import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./components/Login";
import RegisterForm from "./components/RegisterForm";
import DashboardPage from "./components/DashboardPage";
import GroupManagement from "./components/GroupManagement";
import Expense from "./components/Expense";
import Profile from "./components/Profile";


const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/home" element={<DashboardPage />} />
        <Route path="/${groupId}/group" element={<GroupManagement />} />
        <Route path="/expenses" element={<Expense />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
    </Router>
  )
}

export default App;