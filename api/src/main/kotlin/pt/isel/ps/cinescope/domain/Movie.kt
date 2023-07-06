package pt.isel.ps.cinescope.domain


data class Movie(val imdbId: String?, val tmdbId: Int?, val name: String?, val img: String?, val state: MovieState? = null)
data class MovieUserData(val id: Int, val state: MovieState? = null, val lists: List<ListInfo>?)
data class MovieOnLists(val mlid: Int, val userid: Int, val name: String, val mtmdbid: Int, val state: String?)

data class MovieDetailsOutput(
    val movieDetails: MovieDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds?
)

fun checkMovieState(state: String?):Boolean{
    if(state == MovieState.PTW.state || state == MovieState.Watched.state) return true
    return false
}

enum class MovieState(val state: String) {
    PTW("PTW"),
    Watched("Watched");

    companion object {
        fun fromString(string: String?) =
            when(string) {
                "PTW" -> PTW
                "Watched" -> Watched
                //null -> BadRequestException("Movie State cannot be null")
                else -> throw IllegalArgumentException("Movie State does not exists")
            }
    }
}