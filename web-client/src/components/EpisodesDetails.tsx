import * as React from "react";
import { useEffect, useState } from "react";
import { getEpisodeDetails } from "../RequestsHelpers/SearchRequestsHelper";
import { useParams } from "react-router-dom";
import { EpisodeDetailsResults, EMPTY_EPISODE_DETAILS_RESULT, EMPTY_WATCHED_EPISODE, WatchedEpisode, ListResults, EMPTY_LIST_RESULTS_WATCHED_EPISODES } from "../utils/Types";
import { BACKDROP_IMAGE_DOMAIN, getCookie, handleError, showCheckedEpisode } from "../utils/Tools";
import { addWatchedEpisode, getWatchedEpisodesList, removeWatchedEpisode } from "../RequestsHelpers/SeriesRequestsHelper";
import { NavBar } from "./navbar/Navbar";



export function EpisodesDetails() {

    const { serieId, season, episodeNum } = useParams()

    const [episodeInfo, setEpidodeInfo] = useState<EpisodeDetailsResults>(EMPTY_EPISODE_DETAILS_RESULT)

    const [watchedList, setWatchedList] = useState<ListResults<WatchedEpisode>>(EMPTY_LIST_RESULTS_WATCHED_EPISODES)

    const userToken = getCookie('userToken')

    async function getEpisodeInfo() {
        const episode = await getEpisodeDetails(+serieId, +season, +episodeNum)
        setEpidodeInfo(episode)

        if (userToken) {
            const watchedList = await getWatchedEpisodesList(+serieId)
            setWatchedList(watchedList)
        }
    }

    useEffect(() => {
        getEpisodeInfo()
    }, [])

    async function addToWatchedList(ev, episodeId: number, episodeNum: number, episodeSeason: number) {
        const checked = ev.currentTarget.checked
        const checkbox = document.getElementById(`${episodeId}`) as HTMLInputElement;
        if (checked) {
            await addWatchedEpisode(+serieId, episodeNum, episodeSeason)
            checkbox.checked = true
        } else {
            await removeWatchedEpisode(+serieId, episodeNum, episodeSeason)
            checkbox.checked = false
        }
    }

    return (
        <div className='firstDiv'>
            <NavBar />
            <div className="pageDiv">
                <div className="episodeDiv" style={{ backgroundImage: `url(${BACKDROP_IMAGE_DOMAIN}/${episodeInfo.episodeDetails.still_path})` }}>
                    <div className="posterDiv">
                        <div className="imageDiv">
                            <img src={`${BACKDROP_IMAGE_DOMAIN}/${episodeInfo.episodeDetails.still_path}`} alt={episodeInfo.episodeDetails.name} onError={handleError} className="episodeImg" />
                        </div>
                        {userToken && <div className="watchedDiv">
                            <label htmlFor={episodeInfo.episodeDetails.name}>Watched: </label>
                            <input id={`${episodeInfo.episodeDetails.id}`} name={episodeInfo.episodeDetails.name} type="checkbox" checked={showCheckedEpisode(watchedList, +episodeNum, +season)} onClick={(event) => addToWatchedList(event, episodeInfo.episodeDetails.id, +episodeNum, +season)} className="checkmarkEpisode" />
                        </div>}
                    </div>
                    <div className="textInfoEpisodeDiv">
                        <h2 className="contentTitle">{episodeInfo.episodeDetails.name}</h2>
                        <div className="overview">
                            <p>{episodeInfo.episodeDetails.overview}</p>
                        </div>
                        <div className="contentStats">
                            <p> Air Date: {episodeInfo.episodeDetails.date}</p>
                        </div>
                    </div>
                    <div className="apiDiv">
                        {episodeInfo.externalIds.imdb_id && <div className="apiInfo" onClick={() => window.open(`http://www.imdb.com/title/${episodeInfo.externalIds.imdb_id}`)}>
                            <img src="/imdb logo.png" alt="Imdb" className="apiLogo" />
                            <h2 className="apiTitle">IMDB</h2>
                        </div>}
                        {episodeInfo.externalIds.twitter_id && <div className="apiInfo" onClick={() => window.open(`http://www.twitter.com/${episodeInfo.externalIds.twitter_id}`)}>
                            <img src="/twitter logo.png" alt="Twitter" className="apiLogo" />
                            <h2 className="apiTitle">Twitter</h2>
                        </div>}
                        {episodeInfo.externalIds.facebook_id && <div className="apiInfo" onClick={() => window.open(`http://www.facebook.com/${episodeInfo.externalIds.facebook_id}`)}>
                            <img src="/Facebook logo.png" alt="Facebook" className="apiLogo" />
                            <h2 className="apiTitle">Facebook</h2>
                        </div>}
                    </div>
                </div>
            </div>
        </div>
    )
}