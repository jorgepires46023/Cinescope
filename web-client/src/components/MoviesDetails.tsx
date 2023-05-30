import * as React from "react";
import { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getMoviesDetails } from "../RequestsHelpers/SearchRequestsHelper";
import { EMPTY_MOVIE_DETAILS_RESULTS, EMPTY_MOVIE_USER_DATA, EMPTY_PROVIDERS_INFO, EMPTY_USER_LISTS_ELEMS, /* EMPTY_USER_LISTS, */ MovieDetailsResults, MovieUserData, ProviderInfo, /* UserLists, */ UserListsElems } from "../utils/Types";
import { BACKDROP_IMAGE_DOMAIN, IMAGE_DOMAIN, handleError } from "../utils/Tools";
import { UserContext } from "./UserProvider";
import { addMovieToList, changeMovieState, deleteMovieFromList, deleteStateFormMovie, getMoviesListByState, getMoviesLists, getMoviesUserData } from "../RequestsHelpers/MoviesRequestsHelper";

export function MoviesDetails() {
    const userInfo = useContext(UserContext)

    const [movie, setMovie] = useState<MovieDetailsResults>(EMPTY_MOVIE_DETAILS_RESULTS)

    const [showInfo, setShowInfo] = useState<Array<ProviderInfo>>([EMPTY_PROVIDERS_INFO])

    const [showLists, setShowLists] = useState<Array<UserListsElems>>([EMPTY_USER_LISTS_ELEMS])

    const [showListsDiv, setShowListsDiv] = useState<boolean>(false)

    const [isChecked, setIsChecked] = useState<boolean>(false)

    const [movieStateInfo, setMovieStateInfo] = useState<MovieUserData>(EMPTY_MOVIE_USER_DATA)

    const { movieId } = useParams()

    async function getMovieDetailsInfo() {
        const movieDetails = await getMoviesDetails(+movieId)

        setMovie(movieDetails)

        if (movieDetails.watchProviders.results.PT) {
            setShowInfo(movieDetails.watchProviders.results.PT.flatrate)
        }

        if (userInfo.token) {
            try {
                const movieStateInfo = await getMoviesUserData(+movieId, userInfo.token)
                
                if (movieStateInfo) {
                    setMovieStateInfo(movieStateInfo)
                    const select: HTMLSelectElement = document.querySelector('#showState');
                    select.options.namedItem(movieStateInfo.state).selected = true
                }
            } catch (error) {
                if (error.status == 404) {
                    const select: HTMLSelectElement = document.querySelector('#showState')
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

    async function showUserLists(ev) {

        const lists = await getMoviesLists(userInfo.token)

        setShowLists(lists)

        setShowListsDiv(true)

        /* movieStateInfo.lists.map(list => {
            console.log(list.name)
            const checkbox = document.getElementById(`${list.name}`) as HTMLInputElement;
            console.log(checkbox)
            checkbox.checked = true
        }
        ) */
    }

    async function addState(ev) {
        console.log(ev.currentTarget)
        const state = ev.currentTarget.value

        if (state == "PTW") {
            await changeMovieState(+movieId, "PTW", userInfo.token)
        }
        if (state == "Watched") {
            await changeMovieState(+movieId, "Watched", userInfo.token)
        }

        if (state == "Add State") {
            await deleteStateFormMovie(+movieId, userInfo.token)
        }
    }

    async function addToList(ev, listId: number) {
        const checked = ev.currentTarget.checked
        if (checked) {
            await addMovieToList(+movieId, listId, userInfo.token)
        } else {
            await deleteMovieFromList(listId, +movieId, userInfo.token)
           
        }
    }

    function showChecked(listId: number) {
        const list = movieStateInfo.lists.find(list => list.id === listId)
        if(list) {
            return true
        } else {
            return false
        }
    }

    return (
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
                            showLists.map(list =>
                                <div className="showListInfo">
                                    <div className="listInfo">
                                        <label htmlFor={list.name}>{list.name}</label>
                                        <input id={list.name} name={list.name} type="checkbox" checked={showChecked(list.id)} onChange={(event) => addToList(event, list.id)} className="checkmarkLists" />
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
                    {userInfo.token && <div className="buttonsDiv">
                        <div className="buttonState">
                            <select name="show" id="showState" onClick={addState} className="dropdownState">
                                <option id="Add State" value="Add State" className="selected" selected={false} >Add State</option>
                                <option id="PTW" value="PTW" className="selected" selected={false}>PTW</option>
                                <option id="Watched" value="Watched" className="selected" selected={false}>Watched</option>
                            </select>
                        </div>
                        <button className="buttonList" onClick={showUserLists}>Add to List</button>
                    </div>}
                </div>
                <div className="textInfoDiv">
                    <h2 className="contentTitle">{movie.movieDetails.title}</h2>
                    <div className="overview">
                        <p>{movie.movieDetails.overview}</p>
                    </div>
                    <div className="contentStats">
                        <p>Release Date: {movie.movieDetails.release_date}</p>
                        <p> Run Time: {movie.movieDetails.runtime} min</p>
                    </div>
                </div>
                <div className="apiDiv">
                    <div className="apiInfo">
                        <img src="/imdb logo.png" alt="Imdb" className="apiLogo" />
                        <h2 className="apiTitle">IMDB</h2>
                    </div>
                    <div className="apiInfo">
                        <img src="/twitter logo.png" alt="Twitter" className="apiLogo" />
                        <h2 className="apiTitle">Twitter</h2>
                    </div>
                    <div className="apiInfo">
                        <img src="/Facebook logo.png" alt="Facebook" className="apiLogo" />
                        <h2 className="apiTitle">Facebook</h2>
                    </div>

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
                                    <select name="show" id="show" onClick={showSelected} className="dropdown">
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
    )
}