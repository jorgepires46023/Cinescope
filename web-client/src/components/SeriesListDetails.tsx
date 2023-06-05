import * as React from "react";
import { UserContext } from "./UserProvider";
import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { IMAGE_DOMAIN, getSerie, getShowArray, next, previous } from "../utils/Tools";
import { Content, ContentIndexs, EMPTY_CONTENT, EMPTY_LIST_RESULTS, INIT_INDEXS, ListResults } from "../utils/Types";
import { getSeriesList } from "../RequestsHelpers/SeriesRequestsHelper";


export function SeriesListDetails() {

    const userInfo = useContext(UserContext)

    const navigate = useNavigate()

    const { listId } = useParams()

    const [list, setList] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [contentIndexs, setContentIndexs] = useState<ContentIndexs>(INIT_INDEXS)

    const [showSeries, setShowSeries] = useState<Array<Content>>([EMPTY_CONTENT])

    async function getListDetails() {
        const listInfo = await getSeriesList(+listId, userInfo.token)
        setList(listInfo)
        console.log(listInfo)

        const nextSeries = getShowArray(listInfo.results, contentIndexs.init, contentIndexs.end)
        setShowSeries(nextSeries)
        console.log(nextSeries)
    }

    useEffect(() => {
        getListDetails()
    }, [])

    return (
        <div className="pageDiv">
            <div className="listDiv">
                <h2 className="contentTitleList">Your List</h2>
                <div className="listDisplayDiv">
                    <div className="listDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexs, setContentIndexs, list, setShowSeries)} hidden={contentIndexs.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showSeries.map(series =>
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
                            <button className="navigationButton" onClick={() => next(contentIndexs, setContentIndexs, list, setShowSeries)} hidden={contentIndexs.end >= list.results.length} > {">"} </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}