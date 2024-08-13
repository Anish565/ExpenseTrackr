import React, { useState, useEffect} from "react";


interface ExpenseProps {
    id: number;
    description: string;
    amount: number;
    date: Date;
    category: string;
    user: number;
}

interface CategoryProps {
    id: number;
    name: string;
}

function Expense() {
    const [expenses, setExpenses] = useState<ExpenseProps[]>([]);
    const [category, setCategory] = useState<CategoryProps[]>([]);
    const [expense, setExpense] = useState<ExpenseProps>({
        id: 0,
        description: '',
        amount: 0,
        date: new Date(),
        category: '',
        user: 0
    })
    

    useEffect(() => {
        const fetchExpenses = async () => {
            try{
            const response = await fetch('http://localhost:8080/users/expenses/me', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            const data = await response.json();
            setExpenses(data);
        } catch (error) {
                console.error('Error fetching expenses:', error);
            }
        };

        const fetchCategories = async () => {
            try{
                const response = await fetch('http://localhost:8080/categories', { 
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                });
                const data = await response.json();
                setCategory(data);
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        const fetchBarChart = async () => {
            try{
                const response = await fetch('http://localhost:8080/expenses/category-total/bar-graph', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                });
                const data = await response.json();
                console.log(data);
            } catch (error) {
                console.error('Error fetching expenses:', error);
            }
        }

        const fetchPieChart = async () => {
            try{
                const response = await fetch('http://localhost:8080/expenses/category-total/pie-chart', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                });
                const data = await response.json();
                console.log(data);
            } catch (error) {
                console.error('Error fetching expenses:', error);
            }
        }
        fetchExpenses();
        fetchBarChart();
        fetchPieChart();
    }, []);

    const addExpense = async (event: React.FormEvent<HTMLFormElement>) => {
        try {
            const response = await fetch('http://localhost:8080/expenses', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({
                    ...expense
                })
            });
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error adding expense:', error);
        }
    };



    return (
        <div>
            <h1>Expenses</h1>
            <ul>
                {expenses.map(expense => (
                    <li key={expense.id}>
                        <p>Description: {expense.description}</p>
                        <p>Amount: {expense.amount}</p>
                        <p>Date: {expense.date.toLocaleDateString()}</p>
                        <p>Category: {expense.category}</p>
                    </li>
                ))}
            </ul>
            <div>
                <form onSubmit={addExpense}>
                    <label>
                        Description:
                        <input
                        type="text"
                        name="description" 
                        onChange={(e) => setExpense({ ...expense, description: e.target.value })}/>
                    </label>
                    <label>
                        Amount:
                        <input
                        type="number"
                        name="amount" 
                        onChange={(e) => setExpense({ ...expense, amount: Number(e.target.value) })}/>
                    </label>
                    <label>
                        Date:
                        <input
                        type="date"
                        name="date" 
                        onChange={(e) => setExpense({ ...expense, date: new Date(e.target.value) })}/>
                    </label>
                    <label>
                        Category:
                        <select name="category"
                        onChange={(e) => setExpense({ ...expense, category: e.target.value })}>
                            {category.map(category => (
                                <option key={category.id} value={category.name}>
                                    {category.name}
                                </option>
                            ))}
                        </select>
                    </label>
                    <button type="submit">Add Expense</button>
                </form>
            </div>

        </div>
    );
}

export default Expense;