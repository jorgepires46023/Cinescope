import * as React from "react";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { deleteMoviesList, getMoviesList } from "../RequestsHelpers/MoviesRequestsHelper";
import { IMAGE_DOMAIN, deleteContentFromList, deleteList, getMovie, getShowArray, next, previous } from "../utils/Tools";
import { Content, ContentIndexs, EMPTY_CONTENT, EMPTY_LIST_RESULTS, INIT_INDEXS, ListResults } from "../utils/Types";


export function MoviesListDetails() {

    const navigate = useNavigate()

    const { listId } = useParams()

    const [list, setList] = useState<ListResults<Content>>(EMPTY_LIST_RESULTS)

    const [contentIndexs, setContentIndexs] = useState<ContentIndexs>(INIT_INDEXS)

    const [showMovies, setShowMovies] = useState<Array<Content>>([EMPTY_CONTENT])


    async function getListDetails() {
        const listInfo = await getMoviesList(+listId)
        console.log(listInfo)
        setList(listInfo)

        const nextMovies = getShowArray(listInfo.results, contentIndexs.init, contentIndexs.end)
        setShowMovies(nextMovies)
        console.log(list)
    }

    useEffect(() => {
        getListDetails()
    }, [])

    return (
        <div className="pageDiv">
            <div className="listDiv">
                <div className="contentTitleListDiv">
                    <h2 className="contentTitleList">{list.info.name}</h2>
                    <button className="deleteListButton" onClick={() => deleteList(+listId, "Movie", navigate)}> Delete List</button>
                </div>
                <div className="listDisplayDiv">
                    {showMovies.find(elem => elem != EMPTY_CONTENT) ? <div className="listDisplay">
                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => previous(contentIndexs, setContentIndexs, list, setShowMovies)} hidden={contentIndexs.init <= 0} > {"<"} </button>
                        </div>
                        <div className="contentGrid">

                            {showMovies.map(movie =>
                                <div id={`${movie.tmdbId}`} className="contentGridElem" onClick={() => getMovie(movie.tmdbId, navigate)}>
                                    <img src={`${IMAGE_DOMAIN}${movie.img}`} alt={movie.name} className="contentImg" />
                                    <div className="cardOverlayElem">
                                        <p>{movie.name}</p>
                                        <button className="deleteFromListButton" onClick={(event) => deleteContentFromList(event, +listId, movie.tmdbId, "Movie", showMovies, setShowMovies)}>Delete From List</button>
                                    </div>
                                </div>
                            )
                            }

                        </div>

                        <div className="navigationButtonDiv">
                            <button className="navigationButton" onClick={() => next(contentIndexs, setContentIndexs, list, setShowMovies)} hidden={contentIndexs.end >= list.results.length} > {">"} </button>
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