import React, { useEffect } from "react";

interface UserData{
    username: string;
    email: string;
}

interface GroupDetails {
    id: number;
    name: string;
    date: Date;
    admin: string;
}

export default function DashboardPage() {
    const [userData, setUserData] = React.useState<UserData | null>(null);
    const [totalExpenses, setTotalExpenses] = React.useState(0);
    const [groupDetails, setGroupDetails] = React.useState<GroupDetails | null>(null);

    useEffect(() => {
        const fetchUserData = async () => {
            try{
                const response = await fetch(`http://localhost:8081/users/me`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setUserData(await response.json());
            } catch (error) {
                console.error("Error fetching user data:", error);
            }
        };

        const fetchTotalExpenses = async () => {
            try{
                const response = await fetch(`http://localhost:8081/users/balance/me`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setTotalExpenses(await response.json());
            } catch (error) {
                console.error("Error fetching total expenses:", error);
            }
        };

        const fetchGroupDetails = async () => {
            const userId = localStorage.getItem("userId");
            try{
                const response = await fetch(`http://localhost:8081/${userId}/groups`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setGroupDetails(await response.json());
            } catch (error) {
                console.error("Error fetching group details:", error);
            }
        };

        fetchUserData();
        fetchTotalExpenses();
        fetchGroupDetails();
    }, []);



    return (
        <div className="min-h-screen bg-gray-100 p-6">
          {/* Welcome Message */}
          <div className="text-2xl font-semibold mb-4">
            Welcome, {userData?.username || 'User'}!
          </div>
    
          {/* Navigation Links */}
          <nav className="mb-8">
            <ul className="flex space-x-4">
              <li>
                <a href="/groups" className="text-blue-500 hover:text-blue-700">
                  Groups
                </a>
              </li>
              <li>
                <a href="/expenses" className="text-blue-500 hover:text-blue-700">
                  Expenses
                </a>
              </li>
              <li>
                <a href="/profile" className="text-blue-500 hover:text-blue-700">
                  Profile
                </a>
              </li>
              <li>
                <a href="/logout" className="text-blue-500 hover:text-blue-700">
                  Logout
                </a>
              </li>
            </ul>
          </nav>
    
          {/* User Data Summary */}
          <div className="bg-white shadow-md rounded-lg p-6">
            <h2 className="text-xl font-semibold mb-4">Your Summary</h2>
            <div className="grid grid-cols-2 gap-4">
              <div className="bg-blue-100 p-4 rounded-lg text-center">
                <h3 className="text-lg font-medium">Total Expenses</h3>
                <p className="text-2xl">{totalExpenses || 0}</p>
              </div>
              <div className="bg-green-100 p-4 rounded-lg text-center">
                <h3 className="text-lg font-medium">Group Details</h3>
                {/* Have to display the group details */}
                {groupDetails?.name || "No group found"}
              </div>
            </div>
          </div>
        </div>
      );

    }