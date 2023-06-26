import { deleteMovieFromList, deleteMoviesList } from "../RequestsHelpers/MoviesRequestsHelper"
import { deleteSerieFromList, deleteSeriesList } from "../RequestsHelpers/SeriesRequestsHelper"
import { Content, ContentIndexs, ListResults } from "./Types"


export const IMAGE_DOMAIN = "https://image.tmdb.org/t/p/w500"

export const BACKDROP_IMAGE_DOMAIN = "https://image.tmdb.org/t/p/original"

export const DOMAIN_URL = "http://localhost:8080/api"

export function getCookie(cookieName: string) {
    return document.cookie.indexOf(`${cookieName}=`) != -1 ? true : false

}

export function removeCookie(cookieName: string) {
    return document.cookie = `${cookieName}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`
}

export function handleError(ev: React.BaseSyntheticEvent) {
    ev.currentTarget.src = "/Not Found poster.png"

}

export function getShowArray(array: Content[], init: number, end: number) {
    if (init >= 0) {
        if (end > array.length) {
            return array.slice(init)
        } else {
            return array.slice(init, end)
        }
    }
}

export function getMovie(movieId: number, navigate) {
    navigate(`/movies/${movieId}`,)
}

export function getSerie(serieId: number, navigate) {
    navigate(`/series/${serieId}`)
}


export function previous(
    contentIndexs: ContentIndexs,
    setContentIndexs: (ContentIndexs: ContentIndexs) => void,
    list: ListResults<Content>,
    setShowContent: (content: Content[]) => void
) {
    const nextIndexs = { init: contentIndexs.init - 4, end: contentIndexs.end - 4 } as ContentIndexs
    setContentIndexs(nextIndexs)

    const nextContent = getShowArray(list.results, nextIndexs.init, nextIndexs.end)
    setShowContent(nextContent)
}

export function next(
    contentIndexs: ContentIndexs,
    setContentIndexs: (ContentIndexs: ContentIndexs) => void,
    list: ListResults<Content>,
    setShowContent: (content: Content[]) => void
) {
    const nextIndexs = { init: contentIndexs.init + 4, end: contentIndexs.end + 4 } as ContentIndexs
    setContentIndexs(nextIndexs)

    const nextContent = getShowArray(list.results, nextIndexs.init, nextIndexs.end)
    setShowContent(nextContent)

}

export async function deleteList(listId: number, type: string, navigate) {

    switch (type) {
        case "Movie":
            await deleteMoviesList(listId)
            break

        case "Series":
            await deleteSeriesList(listId)
            break
    }

    navigate("/lists")
}

export async function deleteContentFromList(
    event,
    listId: number,
    contentId: number,
    type: string,
    list: Array<Content>,
    setContent: (content: Content[]) => void
) {
    event.stopPropagation()

    switch (type) {
        case "Movie":
            await deleteMovieFromList(listId, contentId)
            break

        case "Series":
            await deleteSerieFromList(listId, contentId)
            break
    }

    const contentDiv = document.getElementById(`${contentId}`)
    contentDiv.remove()
    const array = list.filter(elem => elem.tmdbId != contentId)
    setContent(array)
}