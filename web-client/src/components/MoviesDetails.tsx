import * as React from "react";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getMoviesDetails } from "../RequestsHelpers/SearchRequestsHelper";
import {
    EMPTY_LIST_RESULTS_USER_LISTS, EMPTY_MOVIE_DETAILS_RESULTS, EMPTY_CONTENT_USER_DATA, EMPTY_PROVIDERS_INFO,
    ListResults, MovieDetailsResults, ContentUserData, ProviderInfo, UserListsElems
} from "../utils/Types";
import { BACKDROP_IMAGE_DOMAIN, IMAGE_DOMAIN, addToList, getCookie, handleError, showChecked, showUserLists } from "../utils/Tools";
import { addMovieToList, changeMovieState, deleteMovieFromList, deleteStateFormMovie, getMoviesLists, getMoviesUserData } from "../RequestsHelpers/MoviesRequestsHelper";
import { NavBar } from "./navbar/Navbar";

export function MoviesDetails() {

    const [movie, setMovie] = useState<MovieDetailsResults>(EMPTY_MOVIE_DETAILS_RESULTS)

    const [showInfo, setShowInfo] = useState<Array<ProviderInfo>>([EMPTY_PROVIDERS_INFO])

    const [showLists, setShowLists] = useState<ListResults<UserListsElems>>(EMPTY_LIST_RESULTS_USER_LISTS)

    const [showListsDiv, setShowListsDiv] = useState<boolean>(false)

    const [movieStateInfo, setMovieStateInfo] = useState<ContentUserData>(EMPTY_CONTENT_USER_DATA)

    const { movieId } = useParams()

    const userToken = getCookie("userToken")

    async function getMovieDetailsInfo() {
        const movieDetails = await getMoviesDetails(+movieId)

        setMovie(movieDetails)

        if (movieDetails.watchProviders.results.PT) {
            setShowInfo(movieDetails.watchProviders.results.PT.flatrate)
        }

        if (userToken) {
            const select: HTMLSelectElement = document.querySelector('#showState')
            try {
                const movieStateInfo = await getMoviesUserData(+movieId)

                if (movieStateInfo) {
                    setMovieStateInfo(movieStateInfo)
                    select.options.namedItem(movieStateInfo.state).selected = true
                }
            } catch (error) {
                if (error.status == 404) {
                    select.options.namedItem("Add State").selected = true
                }
            }

        }

    }

    useEffect(() => {
        getMovieDetailsInfo()
    }, [])

    function showSelected(ev) {

        const selected = ev.target.value

        if (selected == "stream") {
            setShowInfo(movie.watchProviders.results.PT.flatrate)
        }
        if (selected == "buy") {
            setShowInfo(movie.watchProviders.results.PT.buy)
        }
        if (selected == "rent") {
            setShowInfo(movie.watchProviders.results.PT.rent)
        }
    }

    async function addState(ev) {
        const state = ev.currentTarget.value

        if (state == "PTW") {
            await changeMovieState(+movieId, "PTW")
        }
        if (state == "Watched") {
            await changeMovieState(+movieId, "Watched")
        }

        if (state == "Add State") {
            await deleteStateFormMovie(+movieId)
            const movieStateInfo = await getMoviesUserData(+movieId)
            setMovieStateInfo(movieStateInfo)
            showLists.results.map(list =>
                showChecked(movieStateInfo, list.id)
            )
        }
    }

    return (
        <div className='firstDiv'>
            <NavBar />
            <div className="pageDiv" style={{ backgroundImage: `url(${BACKDROP_IMAGE_DOMAIN}/${movie.movieDetails.backdrop_path})` }}>
                {
                    showListsDiv &&
                    <div className="showListsDiv">
                        <div className="listsTitleDiv">
                            <button className="exitLists" onClick={() => setShowListsDiv(false)}> {"<"}</button>
                            <h2 className="listsTitle">Your Lists</h2>
                        </div>

                        <div className="showListsGrid">
                            {
                                showLists.results.map(list =>
                                    <div className="showListInfo">
                                        <div className="listInfo">
                                            <label htmlFor={list.name}>{list.name}</label>
                                            <input id={`${list.id}`} name={list.name} type="checkbox" checked={showChecked(movieStateInfo, list.id)}
                                                onClick={(event) => addToList(event, list.id, +movieId, addMovieToList, getMoviesUserData, deleteMovieFromList)} className="checkmarkLists" />
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                    </div>
                }
                <div className="infoDiv" >
                    <div className="posterDiv">
                        <img src={`${IMAGE_DOMAIN}/${movie.movieDetails.poster_path}`} alt={movie.movieDetails.title} onError={handleError} className="posterImg" />
                        {userToken && <div className="buttonsDiv">
                            <div className="buttonState">
                                <select name="show" id="showState" onChange={addState} className="dropdownState">
                                    <option id="Add State" value="Add State" className="selected" selected={false} >Add State</option>
                                    <option id="PTW" value="PTW" className="selected" selected={false}>PTW</option>
                                    <option id="Watched" value="Watched" className="selected" selected={false}>Watched</option>
                                </select>
                            </div>
                            <button className="buttonList" onClick={() => showUserLists(getMoviesLists, getMoviesUserData, setMovieStateInfo, setShowLists, setShowListsDiv, +movieId)}>Add to List</button>
                        </div>}
                    </div>
                    <div className="textInfoDiv">
                        <h2 className="contentTitle">{movie.movieDetails.title}</h2>
                        <div className="overview">
                            <p>{movie.movieDetails.overview}</p>
                        </div>
                        <div className="contentStats">
                            <p>Release Date: {movie.movieDetails.date}</p>
                            <p> Run Time: {movie.movieDetails.runtime} min</p>
                        </div>
                    </div>
                    <div className="apiDiv">
                        {movie.externalIds.imdb_id && <div className="apiInfo" onClick={() => window.open(`http://www.imdb.com/title/${movie.externalIds.imdb_id}`)}>
                            <img src="/imdb logo.png" alt="Imdb" className="apiLogo" />
                            <h2 className="apiTitle">IMDB</h2>
                        </div>}
                        {movie.externalIds.twitter_id && <div className="apiInfo" onClick={() => window.open(`http://www.twitter.com/${movie.externalIds.twitter_id}`)}>
                            <img src="/twitter logo.png" alt="Twitter" className="apiLogo" />
                            <h2 className="apiTitle">Twitter</h2>
                        </div>}
                        {movie.externalIds.facebook_id && <div className="apiInfo" onClick={() => window.open(`http://www.facebook.com/${movie.externalIds.facebook_id}`)}>
                            <img src="/Facebook logo.png" alt="Facebook" className="apiLogo" />
                            <h2 className="apiTitle">Facebook</h2>
                        </div>}
                    </div>
                </div>
                <div className="watchProvidersDiv">

                    {movie.watchProviders.results.PT ? <div className="providersDivMovies">

                        <div className="providersTitleDiv">
                            <h2 className="watchNowTitle">Watch Now</h2>
                        </div>
                        <div className="providersInfoDiv">
                            <div className="dropdownInfoDiv">
                                <div className="dropdownTitleDiv">
                                    <h2>Select Method: </h2>
                                </div>
                                {movie.watchProviders.results.PT &&
                                    <div className="dropdownDiv">
                                        <select name="show" id="show" onChange={showSelected} className="dropdown">
                                            <option value="stream" className="selected">Stream</option>
                                            <option value="buy" className="selected">Buy</option>
                                            <option value="rent" className="selected">Rent</option>
                                        </select>
                                    </div>

                                }
                            </div>
                            <div className="dropdownResultsDivMovies">
                                {
                                    showInfo ? showInfo.map(info =>
                                        <div className="resultsInfo">
                                            <img src={`${IMAGE_DOMAIN}/${info.logo_path}`} alt="" className="providerLogo" />
                                            <h2 className="resultsTitle">{info.provider_name}</h2>
                                        </div>
                                    )
                                        : <h1 className="notAvailableInfo"> This Movie is not available in Portugal</h1>
                                }
                            </div>
                        </div>
                    </div>
                        : <h1 className="notAvailableInfo"> This Movie is not available in Streaming Services in Portugal</h1>
                    }
                </div>
            </div>
        </div>
    )
}