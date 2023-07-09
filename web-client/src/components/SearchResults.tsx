import * as React from "react";
import { useNavigate, useParams } from "react-router-dom";
import { search } from "../RequestsHelpers/SearchRequestsHelper";
import { useEffect, useState } from "react";
import { EMPTY_SEARCH_RESULTS, SearchResults } from "../utils/Types";
import { IMAGE_DOMAIN, handleError } from "../utils/Tools";
import { NavBar } from "./navbar/Navbar";

export function SearchResults() {

    const { query } = useParams()

    const navigate = useNavigate()

    const [searchResults, setSearchResults] = useState<SearchResults>(EMPTY_SEARCH_RESULTS)

    async function getSearchResults() {
        const res = await search(query, null)
        setSearchResults(res)
    }

    useEffect(() => {
        getSearchResults()
    }, [query])

    function getContent(mediaType: string, id: number) {
        if (mediaType == "movie") {
            navigate(`/movies/${id}`)
        }

        if (mediaType == "tv") {
            navigate(`/series/${id}`)
        }
    }

    return (
        <div className='firstDiv'>
            <NavBar />
            <div className="pageDiv">
                <div className="searchTitleDiv">
                    <h1>Search Results</h1>
                </div>
                <div className="searchContentGrid">
                    {searchResults.results.map((result) => {
                        if (result.media_type != "person") {
                            return <div className="cardContent" onClick={() => getContent(result.media_type, result.id)} key={result.id}>
                                <img src={`${IMAGE_DOMAIN}${result.poster_path}`} alt="Not Found poster.png" onError={handleError} className="imgPopular" />
                                <div className="cardOverlay">
                                    <p>{result.title}</p>
                                </div>
                            </div>
                        }
                    })}
                </div>
            </div>
        </div>
    )
}