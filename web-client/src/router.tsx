import * as React from 'react'
import { Outlet, createBrowserRouter, RouterProvider, BrowserRouter, useNavigate } from 'react-router-dom';
import { CreateUser } from './components/CreateUser';
import { Homepage } from './components/Homepage';
import { Login } from './components/Login';
import { NavBar } from './components/navbar/Navbar';
import { useEffect } from 'react';

function DashboardMessages() {
    return (
        <div> <h1>DashBoardMessages</h1> </div>
    )
}

function DashboardTasks() {
    return (
        <div> <h1>DashBoardTasks</h1> </div>
    )
}

const routes = createBrowserRouter([
    {
        "path": "/",
        "element": <DefaultPage />,
        "children": [
            {
                "path": "/home",
                "element": <Homepage />,
                "children": [
                    {
                        "path": "tasks",
                        "element": <DashboardTasks />
                    },
                    {
                        "path": "messages",
                        "element": <DashboardMessages />
                    }
                ]
            }
        ]
    },
    {
        "path": "/login",
        "element": <Login />
    },
    {
        "path": "/createuser",
        "element": <CreateUser />
    }
])

function DefaultPage() {//Component to guarantee that every Webage renders navbar
    const navigate = useNavigate()
    useEffect(() => { 
        navigate("home")
    }, [])
    
    return (
        <div className='firstDiv'>
            <NavBar />
            <Outlet/>
        </div>
    )
}


export function App() {
    return (
        <RouterProvider router={routes} />
    );
}

