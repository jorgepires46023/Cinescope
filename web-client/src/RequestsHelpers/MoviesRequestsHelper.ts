const DOMAIN_URL = "http://localhost:8080/api"

export async function addMovieToList(movieId: number, listId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/${movieId}/list/${listId}`, {
        method: "POST",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then(res => res.json())
}

export async function changeMovieState(movieId: number, state: string, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/${movieId}/state`, {
        method: "POST",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        },
        body:
        JSON.stringify({
            state: state
        })
        })
        .then(res => res.json())
}

export async function getMoviesListByState( state: string, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/state/${state}`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
            
        }
        })
        .then(res => res.json())
}

export async function deleteStateFormMovie(movieId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/${movieId}/state`, {
        method: "DELETE",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then(res => res.json())
}

export async function getMoviesLists(token: string) {
    return await fetch(`${DOMAIN_URL}/movies/lists`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then(res => res.json())
}

export async function getMoviesList(listId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/list/${listId}`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then(res => res.json())
}

export async function createMoviesList(name: string, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/list`, {
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
            if(res.status == 200) {
                return res.json()
            } else {
                throw new Error("Create List Failed");
            }
        })

}

export async function deleteMoviesList(listId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/list/${listId}`, {
        method: "DELETE",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then(res => res.json())
}

export async function deleteMovieFromList(listId: number, movieId: number, token: string) {
    return await fetch(`${DOMAIN_URL}list/${listId}/movie/${movieId}`, {
        method: "DELETE",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then(res => res.json())
}

export async function getMoviesUserData(movieId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/movies/${movieId}`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then(res => res.json())
}