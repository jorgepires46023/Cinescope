import * as React from "react";
import { useEffect, useState } from "react";
import { getMoviesLists } from "../RequestsHelpers/MoviesRequestsHelper";
import { EMPTY_LIST_RESULTS_USER_LISTS, ListResults, UserListsElems } from "../utils/Types";
import { getSeriesLists } from "../RequestsHelpers/SeriesRequestsHelper";
import { useNavigate } from "react-router-dom";


export function Lists() {
    
    const navigate = useNavigate()

    const [moviesLists, setMoviesLists] = useState<ListResults<UserListsElems>>(EMPTY_LIST_RESULTS_USER_LISTS)

    const [seriesLists, setSeriesLists] = useState<ListResults<UserListsElems>>(EMPTY_LIST_RESULTS_USER_LISTS)

    async function getUserLists() {

        const moviesListsInfo = await getMoviesLists()
        setMoviesLists(moviesListsInfo)

        const seriesListsInfo = await getSeriesLists()
        setSeriesLists(seriesListsInfo)
    }


    useEffect(() => {
        getUserLists()
    }, [])

    return (
        <div className="pageDiv">
            <div className="listsMainDiv">
                <div className="listsMainTitleDiv">
                    <h2 className="listsMainTitle">Your Lists</h2>
                    <button className="createListButton" onClick={() => navigate("/createlist")}>Create New List</button>
                </div>
                
                <div className="listsDiv">
                    <div className="listsInfoDiv">
                        <h2 className="listsTitle">Movies Lists</h2>
                        <div className="listsInfo">
                            {
                                moviesLists.results.map(list =>
                                    <button className="listButton" onClick={() => navigate(`/movieslists/${list.id}`)}>{list.name}</button>
                                )
                            }
                        </div>
                    </div>

                    <div className="listsInfoDiv">
                        <h2 className="listsTitle">Series Lists</h2>
                        <div className="listsInfo">
                            {
                                seriesLists.results.map(list =>
                                    <button className="listButton" onClick={() => navigate(`/serieslists/${list.id}`)}>{list.name}</button>
                                )
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}