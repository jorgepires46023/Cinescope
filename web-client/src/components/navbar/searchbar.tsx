import * as React from "react"

export function SearchBar() {

    function search() {

    }

    return (
        <form className="searchForm" onClick={search}>
            <input type="text" className="searchBar" placeholder="Search...." />
            <input type="submit" className="searchBarButton" value=""/>
        </form>
    )
}