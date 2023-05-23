const DOMAIN_URL = "http://localhost:8080/api"

export async function addSerieToList(serieId: number, listId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/list/${listId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function changeSerieState(serieId: number, state: string, token: string) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/state`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        },
        body: JSON.stringify({
            state: state
        })
        })
        .then(res => res.json())
}

export async function removeSerieState(serieId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/state`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function getSeriesByState(state: string, token: string) {
    return await fetch(`${DOMAIN_URL}/series/state/${state}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function addWatchedEpisode(serieId: number, epId: string, epNum: number, seasonNum: number, token: string) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/ep/${epId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        },
        body: JSON.stringify({
            episodeNumber: epNum,
            seasonNumber: seasonNum
        })
        })
        .then(res => res.json())
}

export async function removeWatchedEpisode(serieId: number, epId: string, token: string) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/ep/${epId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function getWatchedEpisodesList(serieId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/watchedep`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function getSeriesLists(token: string) {
    return await fetch(`${DOMAIN_URL}/series/lists`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function getSeriesList(listId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/series/list/${listId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function createSeriesList(name: string, token: string) {
    return await fetch(`${DOMAIN_URL}/series/list`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        },
        body: JSON.stringify({
            name: name
        })
        })
        .then(res => res.json())
}

export async function deleteSeriesList(listId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/series/list/${listId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}

export async function deleteSerieFromList(listId: number, serieId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/series/list/${listId}/serie/${serieId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization":`Bearer ${token}`
        }
        })
        .then(res => res.json())
}