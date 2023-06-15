import * as React from "react";
import { UserContext } from "./UserProvider";
import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getMoviesList } from "../RequestsHelpers/MoviesRequestsHelper";
import { IMAGE_DOMAIN, getCookie, getMovie, getShowArray, next, previous } from "../utils/Tools";
import { Content, ContentIndexs, EMPTY_CONTENT, EMPTY_LIST_RESULTS, INIT_INDEXS, ListResults } from "../utils/Types";


export function MoviesListDetails() {

    const userInfo = useContext(UserContext)

    const navigate = useNavigate()

    const { listId } = useParams()

    const [list, setList] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [contentIndexs, setContentIndexs] = useState<ContentIndexs>(INIT_INDEXS)

    const [showMovies, setShowMovies] = useState<Array<Content>>([EMPTY_CONTENT])


    async function getListDetails() {
        const listInfo = await getMoviesList(+listId, userInfo.token)
        setList(listInfo)

        const nextMovies = getShowArray(listInfo.results, contentIndexs.init, contentIndexs.end)
        setShowMovies(nextMovies)
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
                            <button className="navigationButton" onClick={() => previous(contentIndexs, setContentIndexs, list, setShowMovies)} hidden={contentIndexs.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">
                            {
                                showMovies.map(movie =>
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
                            <button className="navigationButton" onClick={() => next(contentIndexs, setContentIndexs, list, setShowMovies)} hidden={contentIndexs.end >= list.results.length} > {">"} </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}