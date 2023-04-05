import * as React from 'react'
import { Outlet, createBrowserRouter, RouterProvider, BrowserRouter } from 'react-router-dom';
import { Homepage } from './components/Homepage';
import { NavBar } from './components/navbar/Navbar';

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
                "path": "cinescope",
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
    }
])

function DefaultPage() {//Component to guarantee that every Webage renders navbar
    return (
        <div>
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

