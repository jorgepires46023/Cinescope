import * as React from "react";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { BACKDROP_IMAGE_DOMAIN, IMAGE_DOMAIN, getCookie, handleError } from "../utils/Tools";
import {
    EMPTY_LIST_RESULTS_USER_LISTS, EMPTY_CONTENT_USER_DATA, EMPTY_PROVIDERS_INFO, EMPTY_SEASON_DETAILS_RESULTS,
    EMPTY_SERIE_DETAILS_RESULTS, ListResults, ContentUserData, ProviderInfo, SeasonDetailsResults, SerieDetailsResults,
    UserListsElems, WatchedEpisode
} from "../utils/Types";
import { getSeasonDetails, getSeriesDetails } from "../RequestsHelpers/SearchRequestsHelper";
import {
    addSerieToList, addWatchedEpisode, changeSerieState, deleteSerieFromList, getSeriesLists, getSeriesUserData,
    getWatchedEpisodesList, removeSerieState, removeWatchedEpisode
} from "../RequestsHelpers/SeriesRequestsHelper";

export function SeriesDetails() {

    const navigate = useNavigate()

    const [serie, setSerie] = useState<SerieDetailsResults>(EMPTY_SERIE_DETAILS_RESULTS)

    const [showInfoSeason, setShowInfoSeason] = useState<SeasonDetailsResults>(EMPTY_SEASON_DETAILS_RESULTS)

    const [showProviders, setShowProviders] = useState<Array<ProviderInfo>>([EMPTY_PROVIDERS_INFO])

    const [showLists, setShowLists] = useState<ListResults<UserListsElems>>(EMPTY_LIST_RESULTS_USER_LISTS)

    const [showListsDiv, setShowListsDiv] = useState<boolean>(false)

    const [seriesStateInfo, setSeriesStateInfo] = useState<ContentUserData>(EMPTY_CONTENT_USER_DATA)

    const [watchedList, setWatchedList] = useState<ListResults<WatchedEpisode>>({
        info: null,
        results: null
    })

    const { serieId } = useParams()

    const userToken = getCookie("userToken")

    async function getSeriesDetailsInfo() {
        const serieDetails = await getSeriesDetails(+serieId)

        setSerie(serieDetails)

        const season = await getSeasonDetails(+serieId, 1)
        setShowInfoSeason(season)

        if (season) {
            const select: HTMLSelectElement = document.querySelector('#season')
            select.options.namedItem("1").selected = true
        }

        if (serieDetails.watchProviders.results.PT) {
            setShowProviders(serieDetails.watchProviders.results.PT.flatrate)
        }

        if (userToken) {
            const select: HTMLSelectElement = document.querySelector('#showState')
            try {
                const movieStateInfo = await getSeriesUserData(+serieId)

                if (movieStateInfo) {
                    setSeriesStateInfo(movieStateInfo)
                    select.options.namedItem(movieStateInfo.state).selected = true
                }
            } catch (error) {
                if (error.status == 404) {
                    select.options.namedItem("Add State").selected = true
                }
            }

            const watchedListInfo = await getWatchedEpisodesList(+serieId)
            setWatchedList(watchedListInfo)
        }
    }

    useEffect(() => {
        getSeriesDetailsInfo()
    }, [])

    async function showSelectedSeason(ev) {

        const seasonNum = ev.target.value

        const season = await getSeasonDetails(+serieId, seasonNum)

        const watchedListInfo = await getWatchedEpisodesList(+serieId)
        setWatchedList(watchedListInfo)

        setShowInfoSeason(season)
    }

    async function showUserLists(ev) {

        const lists = await getSeriesLists()
        try {
            const movieStateInfo = await getSeriesUserData(+serieId)
            setSeriesStateInfo(movieStateInfo)
        } catch (error) {

        }

        setShowLists(lists)
        setShowListsDiv(true)
    }

    async function addToList(ev, listId: number) {
        const checked = ev.currentTarget.checked
        const checkbox = document.getElementById(`${listId}`) as HTMLInputElement;
        if (checked) {
            await addSerieToList(+serieId, listId)

            try {
                const movieStateInfo = await getSeriesUserData(+serieId)
                const select: HTMLSelectElement = document.querySelector('#showState')
                select.options.namedItem(movieStateInfo.state).selected = true
            } catch (error) { }

            checkbox.checked = true
        } else {
            await deleteSerieFromList(listId, +serieId)
            checkbox.checked = false
        }
    }

    function showChecked(listId: number) {
        if (seriesStateInfo.lists) {
            const list = seriesStateInfo.lists.find(list => list.id === listId)
            if (list) {
                return true
            } else {
                return false
            }
        }
    }

    async function addState(ev) {
        const state = ev.currentTarget.value

        if (state == "PTW") {
            await changeSerieState(+serieId, "PTW")
        }
        if (state == "Watched") {
            await changeSerieState(+serieId, "Watched")
        }
        if (state == "Watching") {
            await changeSerieState(+serieId, "Watching")
        }

        if (state == "Add State") {
            await removeSerieState(+serieId)
            const movieStateInfo = await getSeriesUserData(+serieId)
            setSeriesStateInfo(movieStateInfo)
            showLists.results.map(list =>
                showChecked(list.id)
            )

            const watchedListInfo = await getWatchedEpisodesList(+serieId)
            setWatchedList(watchedListInfo)
        }
    }

    async function addToWatchedList(ev, episodeId: number, episodeNum: number, episodeSeason: number) {
        ev.stopPropagation()
        const checked = ev.currentTarget.checked
        const checkbox = document.getElementById(`${episodeSeason}${episodeId}`) as HTMLInputElement;
        if (checked) {
            await addWatchedEpisode(+serieId, episodeNum, episodeSeason)
            checkbox.checked = true
            try {
                const movieStateInfo = await getSeriesUserData(+serieId)

                const select: HTMLSelectElement = document.querySelector('#showState')
                select.options.namedItem(movieStateInfo.state).selected = true
            } catch (error) { }
        } else {
            await removeWatchedEpisode(+serieId, episodeNum, episodeSeason)
            checkbox.checked = false
        }
    }

    function showCheckedEpisode(episodeNum: number, episodeSeason: number) {
        if (watchedList.results != null) {
            const ep = watchedList.results.find(episode => episode.episode == episodeNum && episode.season == episodeSeason)
            if (ep) {
                return true
            } else {
                return false
            }
        }
    }

    return (
        <div className="pageDiv" style={{ backgroundImage: `url(${BACKDROP_IMAGE_DOMAIN}/${serie.serieDetails.backdrop_path})` }}>
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
                                        <input id={`${list.id}`} name={list.name} type="checkbox" checked={showChecked(list.id)} onClick={(event) => addToList(event, list.id)} className="checkmarkLists" />
                                    </div>
                                </div>
                            )
                        }

                    </div>
                </div>
            }
            <div className="infoDiv">
                <div className="posterDiv">
                    <img src={`${IMAGE_DOMAIN}/${serie.serieDetails.poster_path}`} alt={serie.serieDetails.name} onError={handleError} className="posterImg" />
                    {userToken && <div className="buttonsDiv">
                        <div className="buttonState">
                            <select name="show" id="showState" onChange={addState} className="dropdownState">
                                <option id="Add State" value="Add State" className="selected" selected={false} >Add State</option>
                                <option id="PTW" value="PTW" className="selected" selected={false}>PTW</option>
                                <option id="Watching" value="Watching" className="selected" selected={false}>Watching</option>
                                <option id="Watched" value="Watched" className="selected" selected={false}>Watched</option>
                            </select>
                        </div>
                        <button className="buttonList" onClick={showUserLists}>Add to List</button>
                    </div>}
                </div>
                <div className="textInfoDiv">
                    <h2 className="contentTitle">{serie.serieDetails.name}</h2>
                    <div className="overview">
                        <p>{serie.serieDetails.overview}</p>
                    </div>
                    <div className="contentStats">
                        <p>Status: {serie.serieDetails.status}</p>
                    </div>
                </div>
                <div className="apiDiv">
                    {serie.externalIds.imdb_id && <div className="apiInfo" onClick={() => window.open(`http://www.imdb.com/title/${serie.externalIds.imdb_id}`)}>
                        <img src="/imdb logo.png" alt="Imdb" className="apiLogo" />
                        <h2 className="apiTitle">IMDB</h2>
                    </div>}
                    {serie.externalIds.twitter_id && <div className="apiInfo" onClick={() => window.open(`http://www.twitter.com/${serie.externalIds.twitter_id}`)}>
                        <img src="/twitter logo.png" alt="Twitter" className="apiLogo" />
                        <h2 className="apiTitle">Twitter</h2>
                    </div>}
                    {serie.externalIds.facebook_id && <div className="apiInfo" onClick={() => window.open(`http://www.facebook.com/${serie.externalIds.facebook_id}`)}>
                        <img src="/Facebook logo.png" alt="Facebook" className="apiLogo" />
                        <h2 className="apiTitle">Facebook</h2>
                    </div>}
                </div>
            </div>
            <div className="watchProvidersDivSeries">
                <div className="providersDivSeries">

                    <div className="providersTitleDiv">
                        <h2 className="watchNowTitle">Watch Now</h2>
                    </div>
                    <div className="seasonsDiv">
                        <div className="dropdownInfoDiv">
                            <div className="dropdownTitleDiv">
                                <h2>Seasons</h2>
                            </div>
                            <div className="dropdownDiv">
                                <select name="season" id="season" onChange={showSelectedSeason} className="dropdown">
                                    {
                                        serie.serieDetails.seasons.map(season =>
                                            <option id={`${season.season_number}`} value={season.season_number} className="selected" selected={false}>Season {season.season_number}</option>
                                        )
                                    }
                                </select>
                            </div>
                        </div>

                        <div className="dropdownResultsDivSeries">
                            {
                                showProviders && <div className="providersLogos">
                                    <div className="availableTitleDiv">
                                        <h2 className="availableTitle"> Available In: </h2>
                                    </div>
                                    {
                                        showInfoSeason.watchProviders.results.PT ?
                                            showInfoSeason.watchProviders.results.PT.flatrate.map(info =>
                                                <div className="resultsInfo">
                                                    <img src={`${IMAGE_DOMAIN}/${info.logo_path}`} alt="" className="providerLogo" />
                                                    <h2 className="resultsTitle">{info.provider_name}</h2>
                                                </div>
                                            )
                                            :
                                            <div className="notAvailableInfoDiv">
                                                <h1 className="notAvailableInfo"> This Season is not available in Streaming Services in Portugal</h1>
                                            </div>
                                    }
                                </div>
                            }
                            {
                                <div className="seasonEpisodes">
                                    {
                                        showInfoSeason.seasonDetails.episodes.map(episode =>
                                            <div className="episodeInfo" onClick={() => navigate(`/series/${serieId}/season/${showInfoSeason.seasonDetails.season_number}/episode/${episode.episode_number}`)}>
                                                <p>{`${episode.episode_number}: `}{episode.name}</p>
                                                <div className="episodeButtons" >
                                                    {/* <button className="episodeInfoButton" onClick={() => navigate(`/series/${serieId}/season/${showInfoSeason.seasonDetails.season_number}/episode/${episode.episode_number}`)}>Episode Info</button> */}
                                                    {userToken && <div className="addEpisode">
                                                        <label htmlFor={episode.name}> Viewed: </label>
                                                        <input id={`${showInfoSeason.seasonDetails.season_number}${episode.id}`} name={episode.name} type="checkbox" checked={showCheckedEpisode(episode.episode_number, showInfoSeason.seasonDetails.season_number)} onClick={(event) => addToWatchedList(event, episode.id, episode.episode_number, showInfoSeason.seasonDetails.season_number)} className="checkmarkEpisodes" />
                                                    </div>}
                                                </div>
                                            </div>
                                        )
                                    }
                                </div>
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>)
}