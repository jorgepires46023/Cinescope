package pt.isel.ps.cinescope.domain

import pt.isel.ps.cinescope.services.exceptions.BadRequestException

data class Movie(val imdbId: String?, val tmdbId: Int?, val name: String?, val img: String?, var state: MovieState? = null)

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