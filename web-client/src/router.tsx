import * as React from 'react'
import { createBrowserRouter, RouterProvider, useNavigate } from 'react-router-dom';
import { CreateUser } from './components/CreateUser';
import { Homepage } from './components/Homepage';
import { Login } from './components/Login';
import { useEffect } from 'react';
import { MoviesDetails } from './components/MoviesDetails';
import { SeriesDetails } from './components/SeriesDetails';
import { Movies } from './components/Movies';
import { Series } from './components/Series';
import { SearchResults } from './components/SearchResults';
import { Profile } from './components/Profile';
import { Logout } from './components/Logout';
import { EpisodesDetails } from './components/EpisodesDetails';
import { Lists } from './components/Lists';
import { MoviesListDetails } from './components/MoviesListDetails';
import { SeriesListDetails } from './components/SeriesListDetails';
import { CreateList } from './components/CreateList';

const routes = createBrowserRouter([
    {
        "path": "/",
        "element": <DefaultPage />,
    },
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
        "element": <Movies />
    },
    {
        "path": "/movies/:movieId",
        "element": <MoviesDetails />
    },
    {
        "path": "/series",
        "element": <Series />
    },
    {
        "path": "/series/:serieId/season/:season/episode/:episodeNum",
        "element": <EpisodesDetails />,
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
    },
    {
        "path": "/lists",
        "element": <Lists />
    },
    {
        "path": "/movieslists/:listId",
        "element": <MoviesListDetails />
    },
    {
        "path": "/serieslists/:listId",
        "element": <SeriesListDetails />
    },
    {
        "path": "/createlist",
        "element": <CreateList />
    }
])

function DefaultPage() {//Component to guarantee that every Webage renders navbar
    const navigate = useNavigate()
    useEffect(() => {
        navigate("/home")
    }, [])

    return (
        <div className='firstDiv'></div>
    )
}


export function App() {
    return (
        <RouterProvider router={routes} />
    );
}

