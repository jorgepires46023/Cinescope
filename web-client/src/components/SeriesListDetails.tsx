import * as React from "react";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { IMAGE_DOMAIN, deleteContentFromList, deleteList, getSerie, getShowArray, next, previous } from "../utils/Tools";
import { Content, ContentIndexs, EMPTY_CONTENT, EMPTY_LIST_RESULTS, INIT_INDEXS, ListResults } from "../utils/Types";
import { getSeriesList } from "../RequestsHelpers/SeriesRequestsHelper";


export function SeriesListDetails() {

    const navigate = useNavigate()

    const { listId } = useParams()

    const [list, setList] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [contentIndexs, setContentIndexs] = useState<ContentIndexs>(INIT_INDEXS)

    const [showSeries, setShowSeries] = useState<Array<Content>>([EMPTY_CONTENT])

    async function getListDetails() {
        const listInfo = await getSeriesList(+listId)
        setList(listInfo)

        const nextSeries = getShowArray(listInfo.results, contentIndexs.init, contentIndexs.end)
        setShowSeries(nextSeries)
    }

    useEffect(() => {
        getListDetails()
    }, [])

    useEffect(() => {
        console.log("useEffect")
        console.log(showSeries)
    }, [showSeries])

    return (
        <div className="pageDiv">
            <div className="listDiv">
                <div className="contentTitleListDiv">
                    <h2 className="contentTitleList">{list.info.name}</h2>
                    <button className="deleteListButton" onClick={() => deleteList(+listId, "Series", navigate)}> Delete List</button>
                </div>
                <div className="listDisplayDiv">
                    {showSeries.find(elem => elem != EMPTY_CONTENT) ? <div className="listDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexs, setContentIndexs, list, setShowSeries)} hidden={contentIndexs.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showSeries.map(series =>
                                    <div id={`${series.tmdbId}`} className="contentGridElem" onClick={() => getSerie(series.tmdbId, navigate)}>
                                        <img src={`${IMAGE_DOMAIN}${series.img}`} alt={series.name} className="contentImg" />
                                        <div className="cardOverlayElem">
                                            <p>{series.name}</p>
                                            <button className="deleteFromListButton" onClick={(event) => deleteContentFromList(event, +listId, series.tmdbId, "Series", showSeries, setShowSeries)}>Delete From List</button>
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => next(contentIndexs, setContentIndexs, list, setShowSeries)} hidden={contentIndexs.end >= list.results.length} > {">"} </button>
                        </div>
                    </div>
                        : <div className="contentEmptyListDiv">
                            <h2 className="contentEmptyList">This List Is Empty</h2>
                        </div>
                    }
                </div>
            </div>
        </div>
    )
}