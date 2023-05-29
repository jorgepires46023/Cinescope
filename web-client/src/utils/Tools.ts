
 export const IMAGE_DOMAIN = "https://image.tmdb.org/t/p/w500"

 export const BACKDROP_IMAGE_DOMAIN = "https://image.tmdb.org/t/p/original"

 export function handleError(ev: React.BaseSyntheticEvent) {
    ev.currentTarget.src = "/Not Found poster.png"

}