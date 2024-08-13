import React, { useEffect, useState } from 'react';


interface Group {
    id: number;
    name: string;
    date: Date;
    admin: string;
}

interface Split {
    id: number;
    amount: number;
    groupId: number;
    payerId: number;
    payeeId: number;
    categoryId: number;
    settled: boolean;
    status: string;
}

interface Settlement {
    id: number;
    amount: number;
    settledDate: Date;
    groupId: number;
    payerId: number;
    receiverId: number;
}

interface User {
    id: number;
    username: string;
    email: string;
}

interface Category {
    id: number;
    name: string;
}



function GroupManagement() {
    const [splits, setSplits] = useState<Split[]>([]);
    const [settlements, setSettlements] = useState<Settlement[]>([]);
    const [group, setGroup] = useState<Group| null>(null);
    const [categorys, setCategories] = useState<Category[]>([]);
    const [split,  setSplit] = useState<Split>({
        id: 0,
        amount: 0,
        groupId: 0,
        payerId: 0,
        payeeId: 0,
        categoryId: 0,
        settled: false,
        status: ""
    });
    const [settlement, setSettlement] = useState<Settlement>({
        id: 0,
        amount: 0,
        settledDate: new Date(),
        groupId: 0,
        payerId: 0,
        receiverId: 0
    })
    const [user, setUser] = useState<User[]>([]);

    useEffect(() => {
        const groupId = localStorage.getItem("groupId");
        const fetchGroup = async () => {
            try{
                const groupDetails = await fetch(`http://localhost:8081/groups/${groupId}`,{
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setGroup(await groupDetails.json());
            } catch (error) {
                console.error("Error fetching group details:", error);
            }
        };

        const fetchSplits = async () => {
            try{
                const splits = await fetch(`http://localhost:8081/splits/group/${groupId}`,{
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                    
                });
                setSplits(await splits.json());
            } catch (error) {
                console.error("Error fetching group details:", error);
            }
        };

        const fetchSettlements = async () => {
            try{
                const settlements = await fetch(`http://localhost:8081/settlements/groups/${groupId}`,{
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setSettlements(await settlements.json());
            } catch (error) {
                console.error("Error fetching group details:", error);
            }
        };

        const fetchUser = async () => {
            try{
                const user = await fetch(`http://localhost:8081/${groupId}/users`,{
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setUser(await user.json());
            } catch (error) {
                console.error("Error fetching group details:", error);
            }
        };

        const fetchCategories = async () => {
            try{
                const categories = await fetch(`http://localhost:8081/categories`,{
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                    }
                });
                setCategories(await categories.json());
            } catch (error) {
                console.error("Error fetching group details:", error);
            }
        };

        fetchGroup();
        fetchSplits();
        fetchSettlements();
        fetchUser();
        fetchCategories();
    }, []);

    


    const addSplit = async (e : React.FormEvent<HTMLFormElement>) => {

    if (!group) {
        return <div>Loading...</div>;

    }

    const resposne = fetch(`http://localhost:8081/splits`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(split),
    });

    resposne.then((response) => {
        window.location.reload();
    });

    };  

    const addSettlement = async (e : React.FormEvent<HTMLFormElement>) => {
        if (!group) {
            return <div>Loading...</div>;
    
        }

        const resposne = fetch(`http://localhost:8081/settlements`, {   
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(settlement),
        });

        resposne.then((response) => {
            window.location.reload();
        });
    };

    return (
        <div>
            <h1>{group?.name}</h1>
            <p>Admin: {group?.admin}</p>
            {/* <p>Date: {group.date}</p> */}
            <h2>Splits</h2>
            <ul>
                {splits.map((split) => (
                    <li key={split.id}>
                        Amount: {split.amount}
                    </li>
                ))}
            </ul>
            <h2>Settlements</h2>
            <ul>
                {settlements.map((settlement) => (
                    <li key={settlement.id}>
                        Amount: {settlement.amount}
                    </li>
                ))}
            </ul>
        <div>
            <h2>Add Split</h2>
            <form onSubmit={addSplit}>
                <label>
                    Amount:
                    <input
                        type="number"
                        value={split.amount}
                        onChange={(e) => setSplit({ ...split, amount: Number(e.target.value) })}
                    />
                </label>
                <label>
                    Payer: 
                    <select
                        value={split.payerId}
                        onChange={(e) => setSplit({ ...split, payerId: Number(e.target.value) })}
                    >
                        {user.map((user) => (
                            <option key={user.id} value={user.id}>
                                {user.username}
                            </option>
                        ))}
                    </select>
                </label>
                <label>
                    Payee:
                    <select
                        value={split.payeeId}
                        onChange={(e) => setSplit({ ...split, payeeId: Number(e.target.value) })}
                    >
                        {user.map((user) => (
                            <option key={user.id} value={user.id}>
                                {user.username}
                            </option>
                        ))}
                    </select>
                </label>
                <label>
                    Category:
                    <select
                        value={split.categoryId}
                        onChange={(e) => setSplit({ ...split, categoryId: Number(e.target.value) })}
                    >
                        {categorys.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </label>

                <button type="submit">Add Split</button>
            </form>
        </div>
        </div>
    );
}

export default GroupManagement;    