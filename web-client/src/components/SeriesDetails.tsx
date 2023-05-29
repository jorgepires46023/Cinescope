import * as React from "react";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BACKDROP_IMAGE_DOMAIN, IMAGE_DOMAIN, handleError } from "../utils/Tools";
import { EMPTY_PROVIDERS_INFO, EMPTY_SEASON_DETAILS_RESULTS, EMPTY_SERIE_DETAILS_RESULTS, ProviderInfo, SeasonDetailsResults, SerieDetailsResults } from "../utils/Types";
import { getSeasonDetails, getSeriesDetails } from "../RequestsHelpers/SearchRequestsHelper";

export function SeriesDetails() {

    const [serie, setSerie] = useState<SerieDetailsResults>(EMPTY_SERIE_DETAILS_RESULTS)

    const [showInfoSeason, setShowInfoSeason] = useState<SeasonDetailsResults>(EMPTY_SEASON_DETAILS_RESULTS)

    const [showProviders, setShowProviders] = useState<Array<ProviderInfo>>([EMPTY_PROVIDERS_INFO])

    const { serieId } = useParams()

    async function getSeriesDetailsInfo() {
        const serieDetails = await getSeriesDetails(+serieId)
        console.log("Serie =>")
        console.log(serieDetails)
        setSerie(serieDetails)
        if (serieDetails.watchProviders.results.PT) {
            setShowProviders(serieDetails.watchProviders.results.PT.flatrate)

            const season = await getSeasonDetails(+serieId, 1)
            console.log("Season =>")
            console.log(season)
            setShowInfoSeason(season)
        }
    }

    useEffect(() => {
        getSeriesDetailsInfo()
    }, [])

    async function showSelectedSeason(ev) {

        const seasonNum = ev.target.value

        const season = await getSeasonDetails(+serieId, seasonNum)

        setShowInfoSeason(season)
    }

    return (<div className="pageDiv" style={{ backgroundImage: `url(${BACKDROP_IMAGE_DOMAIN}/${serie.serieDetails.backdrop_path})` }}>

        <div className="infoDiv">
            <div className="posterDiv">
                <img src={`${IMAGE_DOMAIN}/${serie.serieDetails.poster_path}`} alt={serie.serieDetails.name} onError={handleError} className="posterImg" />
    
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
        <div className="watchProvidersDivSeries">
            {serie.watchProviders.results.PT ?
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
                                <select name="season" id="season" onClick={showSelectedSeason} className="dropdown">
                                    {
                                        serie.serieDetails.seasons.map(season =>
                                            <option value={season.season_number} className="selected">Season {season.season_number}</option>
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
                                            <div className="episodeInfo">
                                                <p>{`Episode ${episode.episode_number}: `}{episode.name}</p>
                                            </div>
                                        )
                                    }
                                </div>
                            }
                            {/* {
                                !showInfoSeason.watchProviders.results.PT && <h1 className="notAvailableInfo"> This Season is not available in Streaming Services in Portugal</h1>
                            } */}
                        </div>
                    </div>
                </div>
                : <h1 className="notAvailableInfo"> This Series is not available in Streaming Services in Portugal</h1>
            }
        </div>
    </div>)
}