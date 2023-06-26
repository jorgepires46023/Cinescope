import { DOMAIN_URL } from "../utils/Tools"

export async function addSerieToList(serieId: number, listId: number) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/list/${listId}`, {
        method: "POST",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}

export async function changeSerieState(serieId: number, state: string) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/state`, {
        method: "POST",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            state: state
        })
    })
        .then(res => res.json())
}

export async function removeSerieState(serieId: number) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/state`, {
        method: "DELETE",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}

export async function getSeriesByState(state: string) {
    return await fetch(`${DOMAIN_URL}/series/state/${state}`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}

export async function addWatchedEpisode(serieId: number, epNum: number, seasonNum: number) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/ep`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        credentials: 'include',
        body: JSON.stringify({
            episodeNumber: epNum,
            seasonNumber: seasonNum
        })
    })
        .then(res => res.json())
}

export async function removeWatchedEpisode(serieId: number, epNum: number, seasonNum: number) {
    return await fetch(`${DOMAIN_URL}series/${serieId}/season/${seasonNum}/ep/${epNum}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        credentials: 'include',
        body: JSON.stringify({
            episodeNumber: epNum,
            seasonNumber: seasonNum
        })
    })
        .then(res => res.json())
}

export async function getWatchedEpisodesList(serieId: number) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}/watchedep`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => {
            if (res.status == 200) {
                return res.json()
            }
            if(res.status == 404) {
                throw new Error("Not Found")
            }
        }
        )
        .catch(() => {return { results: null }})
}

export async function getSeriesLists() {
    return await fetch(`${DOMAIN_URL}/series/lists`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}

export async function getSeriesList(listId: number) {
    return await fetch(`${DOMAIN_URL}/series/list/${listId}`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}

export async function createSeriesList(name: string) {
    return await fetch(`${DOMAIN_URL}/series/list`, {
        method: "POST",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            name: name
        })
    })
        .then(res => {
            if (res.status == 200) {
                return res.json()
            } else {
                throw new Error("Create List Failed");
            }
        })
}

export async function deleteSeriesList(listId: number) {
    return await fetch(`${DOMAIN_URL}/series/list/${listId}`, {
        method: "DELETE",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}

export async function deleteSerieFromList(listId: number, serieId: number) {
    return await fetch(`${DOMAIN_URL}/series/list/${listId}/serie/${serieId}`, {
        method: "DELETE",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}

export async function getSeriesUserData(serieId: number) {
    return await fetch(`${DOMAIN_URL}/series/${serieId}`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => res.json())
}