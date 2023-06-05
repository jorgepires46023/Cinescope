import { Content, ContentIndexs, ListResults } from "./Types"


export const IMAGE_DOMAIN = "https://image.tmdb.org/t/p/w500"

export const BACKDROP_IMAGE_DOMAIN = "https://image.tmdb.org/t/p/original"

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