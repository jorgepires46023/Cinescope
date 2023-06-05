import * as React from "react";
import { useContext, useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { UserContext } from "./UserProvider";
import { getMoviesListByState } from "../RequestsHelpers/MoviesRequestsHelper";
import { ContentIndexs, EMPTY_CONTENT, INIT_INDEXS, Content, ListResults, EMPTY_LIST_RESULTS } from "../utils/Types";
import { IMAGE_DOMAIN, getMovie, getShowArray, next, previous } from "../utils/Tools";


export function Movies() {
    const userInfo = useContext(UserContext)

    const navigate = useNavigate()

    const [ptwMovies, setPTWMovies] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [watchedMovies, setWatchedMovies] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [showPTWMovies, setShowPTWMovies] = useState<Array<Content>>([EMPTY_CONTENT])

    const [showWatchedMovies, setShowWatchedMovies] = useState<Array<Content>>([EMPTY_CONTENT])

    const [contentIndexsPTW, setContextIndexsPTW] = useState<ContentIndexs>(INIT_INDEXS)

    const [contentIndexsWatched, setContextIndexsWatched] = useState<ContentIndexs>(INIT_INDEXS)

    async function getMoviesByState() {

        const ptwMoviesInfo = await getMoviesListByState("PTW", userInfo.token)

        setPTWMovies(ptwMoviesInfo)

        setShowPTWMovies(getShowArray(ptwMoviesInfo.results, contentIndexsPTW.init, contentIndexsPTW.end))

        const watchedMovies = await getMoviesListByState("Watched", userInfo.token)
        setWatchedMovies(watchedMovies)

        setShowWatchedMovies(getShowArray(watchedMovies.results, contentIndexsWatched.init, contentIndexsWatched.end))

    }

    useEffect(() => {
        if (!userInfo.token) {
            navigate("/login")
        } else {
            getMoviesByState()
        }
    }, [])

    return (
        <div className="pageDiv">
            <div className="stateListsDiv">
                <div className="moviesDiv">
                    <h2 className="contentTitle">PTW Movies</h2>
                    <div className="contentDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexsPTW, setContextIndexsPTW, ptwMovies, setShowPTWMovies)} hidden={contentIndexsPTW.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showPTWMovies.map(movie =>
                                    <div className="contentGridElem" onClick={() => getMovie(movie.tmdbId, navigate)}>
                                        <img src={`${IMAGE_DOMAIN}${movie.img}`} alt={movie.name} className="contentImg" />
                                        <div className="cardOverlayElem">
                                            <p>{movie.name}</p>
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => next(contentIndexsPTW, setContextIndexsPTW, ptwMovies, setShowPTWMovies)} hidden={contentIndexsPTW.end >= ptwMovies.results.length} > {">"} </button>
                        </div>
                    </div>
                </div>

                <div className="moviesDiv">
                    <h2 className="contentTitle">Watched Movies</h2>
                    <div className="contentDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexsWatched, setContextIndexsWatched, watchedMovies, setShowWatchedMovies)} hidden={contentIndexsWatched.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showWatchedMovies.map(movie =>
                                    <div className="contentGridElem" onClick={() => getMovie(movie.tmdbId, navigate)}>
                                        <img src={`${IMAGE_DOMAIN}${movie.img}`} alt={movie.name} className="contentImg" />
                                        <div className="cardOverlayElem">
                                            <p>{movie.name}</p>
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => next(contentIndexsWatched, setContextIndexsWatched, watchedMovies, setShowWatchedMovies)} hidden={contentIndexsWatched.end >= watchedMovies.results.length} > {">"} </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}