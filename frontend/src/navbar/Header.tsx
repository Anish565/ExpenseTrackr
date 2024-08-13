import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Header: React.FC = () => {
  const { isAuthenticated, logout } = useAuth();

  return (
    <header className="bg-blue-600 text-white p-4 shadow-md">
      <div className="container mx-auto flex justify-between items-center">
        <h1 className="text-2xl font-bold">
          <Link to="/dashboard">MyApp</Link>
        </h1>
        <nav className="space-x-4">
          {isAuthenticated ? (
            <>
              <Link to="/dashboard" className="hover:underline">Dashboard</Link>
              <Link to="/profile" className="hover:underline">Profile</Link>
              <Link to="/groups" className="hover:underline">Groups</Link>
              <Link to="/expenses" className="hover:underline">Expenses</Link>
              <button onClick={logout} className="hover:underline">Logout</button>
            </>
          ) : (
            <Link to="/login" className="hover:underline">Login</Link>
          )}
        </nav>
      </div>
    </header>
  );
};

export default Header;
