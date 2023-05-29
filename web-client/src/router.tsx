import * as React from 'react'
import { Outlet, createBrowserRouter, RouterProvider, BrowserRouter, useNavigate } from 'react-router-dom';
import { CreateUser } from './components/CreateUser';
import { Homepage } from './components/Homepage';
import { Login } from './components/Login';
import { NavBar } from './components/navbar/Navbar';
import { useEffect } from 'react';
import { MoviesDetails } from './components/MoviesDetails';
import { SeriesDetails } from './components/SeriesDetails';
import { Movies } from './components/Movies';
import { Series } from './components/Series';
import { SearchResults } from './components/SearchResults';
import { Profile } from './components/Profile';
import { Logout } from './components/Logout';

const routes = createBrowserRouter([
    {
        "path": "/",
        "element": <DefaultPage />,
        "children": [
            {
                "path": "/home",
                "element": <Homepage />
            },
            {
                "path": "/login",
                "element": <Login />
            },
            {
                "path": "/logout",
                "element": <Logout />
            },
            {
                "path": "/createuser",
                "element": <CreateUser />
            },
            {
                "path": "/movies",
                "element": <Movies />,
                "children": [
                    
                ]
            },
            {
                "path": "/movies/:movieId",
                "element": <MoviesDetails />
            },
            {
                "path": "/series",
                "element": <Series />,
                "children": [
                ]
            },
            {
                "path": "/series/:serieId",
                "element": <SeriesDetails />
            },
            {
                "path": "/search/:query",
                "element": <SearchResults />
            },
            {
                "path": "/profile",
                "element": <Profile />
            }
        ]
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
            <Outlet />
        </div>
    )
}


export function App() {
    return (
        <RouterProvider router={routes} />
    );
}

