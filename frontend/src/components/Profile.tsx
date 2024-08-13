import React, { useEffect, useState } from 'react';

interface UserInterface {
    username: string;
    email: string;
    password?: string;
    newPassword?: string;
    confirmPassword?: string;
}

function Profile() {
    const [user, setUser] = useState<UserInterface>({
        username: '',
        email: '',
    });

    const [passwordChange, setPasswordChange] = useState<UserInterface>({
        username: '',
        email: '',
        password: '',
        newPassword: '',
        confirmPassword: '',
    });
    
    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await fetch('http://localhost:8080/users/me', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                });

                const data = await response.json();

                setUser(data);
            } catch (error) {
                console.error('Error fetching user:', error);
            }
        };

        fetchUser();
    }, []);

    const handleUpdate = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/users/me', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(user)
            });

            const data = await response.json();

            console.log(data);

            window.location.reload();

        } catch (error) {
            console.error('Error updating user:', error);
        }
    };

    const handleChangePassword = async (event: React.ChangeEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (passwordChange.newPassword !== passwordChange.confirmPassword) {
            alert('Passwords do not match');
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/users/me', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(passwordChange)
            });

            const data = await response.json();

            console.log(data);

            window.location.reload();

        } catch (error) {
            console.error('Error updating user:', error);

        }

    };

    return (
        <div>
            <h1>Profile</h1>
            <form onSubmit={handleUpdate}>
                <label>
                    Username:
                    <input
                        type="text"
                        value={user.username}
                        onChange={(event) => setUser({ ...user, username: event.target.value })}
                    />  
                </label>
                <label> 
                    Email:
                    <input
                        type="email"
                        value={user.email}
                        onChange={(event) => setUser({ ...user, email: event.target.value })}
                    />
                </label>
                <button type="submit">Update</button>
            </form>

            <form onSubmit={handleChangePassword}>
                <label>
                    Old Password:
                    <input
                        type="password"
                        value={passwordChange.password}
                        onChange={(event) => setPasswordChange({ ...passwordChange, password: event.target.value })}
                    />
                </label>
                <label>
                    New Password:
                    <input
                        type="password"
                        value={passwordChange.newPassword}
                        onChange={(event) => setPasswordChange({ ...passwordChange, newPassword: event.target.value })}
                    />
                </label>
                <label>
                    Confirm Password:
                    <input
                        type="password"
                        value={passwordChange.confirmPassword}
                        onChange={(event) => setPasswordChange({ ...passwordChange, confirmPassword: event.target.value })}
                    />
                </label>
                <button type="submit">Change Password</button>
            </form>
        </div>
    );
}

export default Profile;