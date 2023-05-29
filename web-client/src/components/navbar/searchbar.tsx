import * as React from "react"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

export function SearchBar() {
    const [searchQuery, setSearchQuery] = useState<string>(null)

    const navigate = useNavigate()

    function search(ev: React.FormEvent<HTMLInputElement>) {
        ev.preventDefault()
        navigate(`search/${searchQuery}`)
    }

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        setSearchQuery(ev.currentTarget.value)
    }

    return (
        <form className="searchForm">
            <input type="text" className="searchBar" placeholder="Search...." onChange={handleChange} />
            <input type="submit" className="searchBarButton" value="" onClick={search} />
        </form>
    )
}