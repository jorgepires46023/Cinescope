package pt.isel.ps.cinescope.domain

import pt.isel.ps.cinescope.services.exceptions.BadRequestException

data class Movie(val imdbId: String?, val tmdbId: String?, val name: String?, val img: String?)

fun checkMovieState(state: String?):Boolean{
    if(state.isNullOrBlank()) return false
    if(state != MovieState.PTW.state || state != MovieState.Watched.state) return false
    return true
}

enum class MovieState(val state: String) {
    PTW("PTW"),
    Watched("Watched");

    companion object {
        fun fromString(string: String?) =
            when(string) {
                "PTW" -> PTW
                "Watched" -> Watched
                null -> BadRequestException("Movie State cannot be null")
                else -> throw IllegalArgumentException("Movie State does not exists")
            }
    }
}