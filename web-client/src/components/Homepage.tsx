import * as React from "react"
import { useNavigate } from "react-router-dom"
import { getPopularMovies, getPopularSeries } from "../RequestsHelpers/SearchRequestsHelper"
import { useEffect, useState } from "react"
import { EMPTY_LIST, List } from "../utils/Types"
import { IMAGE_DOMAIN, getMovie, getSerie } from "../utils/Tools"
import { NavBar } from "./navbar/Navbar"

export function Homepage() {

    const navigate = useNavigate()

    const [moviesList, setMoviesList] = useState<List>(EMPTY_LIST)
    const [seriesList, setSeriesList] = useState<List>(EMPTY_LIST)

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


    return (
        <div className='firstDiv'>
            <NavBar />
            <div className="pageDiv">
                <div className="showDiv">
                    <h2 className="h2ClassGrid">Popular Movies</h2>

                    <div className="popularContentGrid">
                        {moviesList.results.map(movie =>
                            <div className="cardContent" onClick={() => getMovie(movie.id, navigate)} key={movie.id}>
                                <img src={`${IMAGE_DOMAIN}${movie.poster_path}`} alt={movie.title} className="imgPopular" />
                                <div className="cardOverlay">
                                    <p>{movie.title}</p>
                                </div>
                            </div>
                        )}
                    </div>

                </div>

                <div className="showDiv">
                    <h2 className="h2ClassGrid">Popular Series</h2>

                    <div className="popularContentGrid" >
                        {seriesList.results.map(series =>
                            <div className="cardContent" onClick={() => getSerie(series.id, navigate)} key={series.id}>
                                <img src={`${IMAGE_DOMAIN}${series.poster_path}`} alt={series.title} className="imgPopular" />
                                <div className="cardOverlay">
                                    <p>{series.title}</p>
                                </div>
                            </div>
                        )}
                    </div>

                </div>
            </div>
        </div>
    )
}