import * as React from "react";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Content, ContentIndexs, EMPTY_CONTENT, EMPTY_LIST_RESULTS, INIT_INDEXS, ListResults } from "../utils/Types";
import { IMAGE_DOMAIN, getCookie, getSerie, getShowArray, next, previous } from "../utils/Tools";
import { getSeriesByState } from "../RequestsHelpers/SeriesRequestsHelper";


export function Series() {

    const navigate = useNavigate()

    const [ptwSeries, setPTWSeries] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [watchedSeries, setWatchedSeries] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [watchingSeries, setWatchingSeries] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [showPTWSeries, setShowPTWSeries] = useState<Array<Content>>([EMPTY_CONTENT])

    const [showWatchedSeries, setShowWatchedSeries] = useState<Array<Content>>([EMPTY_CONTENT])

    const [showWatchingSeries, setShowWatchingSeries] = useState<Array<Content>>([EMPTY_CONTENT])

    const [contentIndexsPTW, setContextIndexsPTW] = useState<ContentIndexs>(INIT_INDEXS)

    const [contentIndexsWatched, setContextIndexsWatched] = useState<ContentIndexs>(INIT_INDEXS)

    const [contentIndexsWatching, setContextIndexsWatching] = useState<ContentIndexs>(INIT_INDEXS)

    const userToken = getCookie("userToken")

    async function getSeriesByStateInfo() {

        const ptwSeriesInfo = await getSeriesByState("PTW")
        setPTWSeries(ptwSeriesInfo)
        setShowPTWSeries(getShowArray(ptwSeriesInfo.results, contentIndexsPTW.init, contentIndexsPTW.end))

        const watchedSeriesInfo = await getSeriesByState("Watched")
        setWatchedSeries(watchedSeriesInfo)
        setShowWatchedSeries(getShowArray(watchedSeriesInfo.results, contentIndexsWatched.init, contentIndexsWatched.end))

        const watchingSeriesInfo = await getSeriesByState("Watching")
        setWatchingSeries(watchingSeriesInfo)
        setShowWatchingSeries(getShowArray(watchingSeriesInfo.results, contentIndexsWatching.init, contentIndexsWatching.end))
    }

    useEffect(() => {
        if (!userToken) {
            navigate("/login")
        } else {
            getSeriesByStateInfo()
        }
    }, [])

    return (
        <div className="pageDiv">
            <div className="stateListsDiv">
                <div className="seriesDiv">
                    <h2 className="stateListTitle">PTW Series</h2>
                    <div className="contentDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexsPTW, setContextIndexsPTW, ptwSeries, setShowPTWSeries)} hidden={contentIndexsPTW.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showPTWSeries.map(series =>
                                    <div className="contentGridElem" onClick={() => getSerie(series.tmdbId, navigate)}>
                                        <img src={`${IMAGE_DOMAIN}${series.img}`} alt={series.name} className="contentImg" />
                                        <div className="cardOverlayElem">
                                            <p>{series.name}</p>
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => next(contentIndexsPTW, setContextIndexsPTW, ptwSeries, setShowPTWSeries)} hidden={contentIndexsPTW.end >= ptwSeries.results.length} > {">"} </button>
                        </div>
                    </div>
                </div>

                <div className="seriesDiv">
                    <h2 className="stateListTitle">Watching Series</h2>
                    <div className="contentDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexsWatching, setContextIndexsWatching, watchingSeries, setShowWatchingSeries)} hidden={contentIndexsWatching.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showWatchingSeries.map(series =>
                                    <div className="contentGridElem" onClick={() => getSerie(series.tmdbId, navigate)}>
                                        <img src={`${IMAGE_DOMAIN}${series.img}`} alt={series.name} className="contentImg" />
                                        <div className="cardOverlayElem">
                                            <p>{series.name}</p>
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => next(contentIndexsWatching, setContextIndexsWatching, watchingSeries, setShowWatchingSeries)} hidden={contentIndexsWatching.end >= watchingSeries.results.length} > {">"} </button>
                        </div>
                    </div>
                </div>

                <div className="seriesDiv">
                    <h2 className="stateListTitle">Watched Series</h2>
                    <div className="contentDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexsWatched, setContextIndexsWatched, watchedSeries, setShowWatchedSeries)} hidden={contentIndexsWatched.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showWatchedSeries.map(series =>
                                    <div className="contentGridElem" onClick={() => getSerie(series.tmdbId, navigate)}>
                                        <img src={`${IMAGE_DOMAIN}${series.img}`} alt={series.name} className="contentImg" />
                                        <div className="cardOverlayElem">
                                            <p>{series.name}</p>
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => next(contentIndexsWatched, setContextIndexsWatched, watchedSeries, setShowWatchedSeries)} hidden={contentIndexsWatched.end >= watchedSeries.results.length} > {">"} </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}