import { DOMAIN_URL } from "../utils/Tools"

export async function getMoviesDetails(movieId: number) {
    return await fetch(`${DOMAIN_URL}/api_movies/${movieId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function search(query: string, pageNum: number | null) {
    return await fetch(`${DOMAIN_URL}/search/${query}${pageNum != null ? `?page=${pageNum}` : ''}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function getSeriesDetails(serieId: number) {
    return await fetch(`${DOMAIN_URL}/api_series/${serieId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function getSeasonDetails(serieId: number, seasonNum: number) {
    return await fetch(`${DOMAIN_URL}/api_series/${serieId}/season/${seasonNum}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function getEpisodeDetails(serieId: number, seasonNum: number, epNum: number) {
    return await fetch(`${DOMAIN_URL}/api_series/${serieId}/season/${seasonNum}/ep/${epNum}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function getMovieRecommendations(movieId: number, pageNum: number | null) {
    return await fetch(`${DOMAIN_URL}/api_movies/${movieId}/recommendations${pageNum != null ? `?page=${pageNum}` : ''}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function getSeriesRecommendations(serieId: number,pageNum: number | null) {
    return await fetch(`${DOMAIN_URL}/api_series/${serieId}/recommendations${pageNum != null ? `?page=${pageNum}` : ''}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function getPopularMovies(pageNum: number | null) {
    return await fetch(`${DOMAIN_URL}/api_movies/popular${pageNum != null ? `?page=${pageNum}` : ''}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}

export async function getPopularSeries(pageNum: number | null) {
    return await fetch(`${DOMAIN_URL}/api_series/popular${pageNum != null ? `?page=${pageNum}` : ''}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
        })
        .then(res => res.json())
}


