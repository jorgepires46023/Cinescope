import * as React from "react"
import { Outlet, useSearchParams } from "react-router-dom"
import { NavBar } from "./navbar/Navbar"
import { getPopularMovies, getPopularSeries } from "../RequestsHelpers/SearchRequestsHelper"
import { useEffect, useState } from "react"

type ListElem = {
    poster_path: string,
    id: number,
    title: string,
    name: string | null,
    media_type: string,
    popularity: number
}

type List = {
    page: number,
    results: [ListElem] | [],
    total_results: number,
    total_pages: number
}


export function Homepage() {

    const [moviesList, setMoviesList] = useState<List>(
        {
            page: 0,
            results: [],
            total_results: 0,
            total_pages: 0
        }
    )
    const [seriesList, setSeriesList] = useState<List>(
        {
            page: 0,
            results: [],
            total_results: 0,
            total_pages: 0
        }
    )

    async function getPopularMoviesInfo() {
        const movies = await getPopularMovies(1)
        setMoviesList(movies)
    }

    async function getPopularSeriesInfo() {
        const series = await getPopularSeries(1)
        setSeriesList(series)
    }

    useEffect(() => {
        getPopularMoviesInfo()
        getPopularSeriesInfo()
    }, [])
    console.log("Series => ")
    console.log(seriesList)
    console.log("Movies => ")
    console.log(moviesList)
    return (
        <div className="pageDiv">
            {/*<div className="titleDiv">
                <h1>Cinescope</h1>
    </div>*/}

            <div className="showDiv">
                <h2 className="h2ClassGrid">Popular Movies</h2>
                <div className="gridDiv">
                <div className="popularContentGrid">
                    {moviesList.results.map(movie =>
                        <div className="cardContent">
                             <img src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`} alt={movie.title} className="imgPopular" />
                        </div>
                    )}
                </div>
                </div>
            </div>

            <div className="showDiv">
                <h2 className="h2ClassGrid">Popular Series</h2>
                <div className="gridDiv">
                <div className="popularContentGrid">
                    {seriesList.results.map(series =>
                        <div className="cardContent">
                            <img src={`https://image.tmdb.org/t/p/w500${series.poster_path}`} alt={series.title} className="imgPopular" />
                        </div>
                    )}
                </div>
                </div>
            </div>
        </div>
    )
}