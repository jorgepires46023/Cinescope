package pt.isel.ps.cinescope.domain

data class Movie(val imdbId: String?, val tmdbId: String?, val name: String?, val img: String?)

fun checkMovieState(state: String?):Boolean{
    if(state.isNullOrBlank()) return false
    if(state != MovieState.PTW || state != MovieState.Watched) return false
    return true
}

object MovieState {
    const val PTW = "PTW"
    const val Watched = "Watched"
}