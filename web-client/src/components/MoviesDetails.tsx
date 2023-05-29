import * as React from "react";
import { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getMoviesDetails } from "../RequestsHelpers/SearchRequestsHelper";
import { EMPTY_MOVIE_DETAILS_RESULTS, EMPTY_PROVIDERS_INFO, EMPTY_USER_LISTS_ELEMS, /* EMPTY_USER_LISTS, */ MovieDetailsResults, ProviderInfo, /* UserLists, */ UserListsElems } from "../utils/Types";
import { BACKDROP_IMAGE_DOMAIN, IMAGE_DOMAIN, handleError } from "../utils/Tools";
import { UserContext } from "./UserProvider";
import { changeMovieState, deleteStateFormMovie, getMoviesLists } from "../RequestsHelpers/MoviesRequestsHelper";

export function MoviesDetails() {
    const userInfo = useContext(UserContext)

    const [movie, setMovie] = useState<MovieDetailsResults>(EMPTY_MOVIE_DETAILS_RESULTS)

    const [showInfo, setShowInfo] = useState<Array<ProviderInfo>>([EMPTY_PROVIDERS_INFO])

    const [showLists, setShowLists] = useState<Array<UserListsElems>>([EMPTY_USER_LISTS_ELEMS])

    const [showListsDiv, setShowListsDiv] = useState<boolean>(false)

    const [isChecked, setIsChecked] = useState<boolean>(false) 

    const { movieId } = useParams()

    async function getMovieDetailsInfo() {
        const movieDetails = await getMoviesDetails(+movieId)
        console.log(movieDetails)
        setMovie(movieDetails)
        if (movieDetails.watchProviders.results.PT) {
            setShowInfo(movieDetails.watchProviders.results.PT.flatrate)
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

    function addToWatchList(ev) {

    }

    async function showUserLists(ev) {

        const lists = await getMoviesLists(userInfo.token)

        setShowLists(lists)

        setShowListsDiv(true)
    }

    async function addToPTW() {

        if(!isChecked) {
            await changeMovieState(movie.movieDetails.id, "PTW", userInfo.token)
            setIsChecked(true)
        } else {
            await deleteStateFormMovie(movie.movieDetails.id, userInfo.token)
            setIsChecked(false)
        }
    }

    function addToList(ev) {



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
                                        <label htmlFor="PTW">{list.name}</label>
                                        <input id="PTW" name="PTW" type="checkbox" className="checkmarkLists"/>
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
                        <div className="buttonPTW">
                            <label htmlFor="PTW">Add to PTW:</label>
                            <input id="PTW" name="PTW" type="checkbox" className="checkmark" onClick={addToPTW} />
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