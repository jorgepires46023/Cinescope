package pt.isel.ps.cinescope.domain

import pt.isel.ps.cinescope.services.exceptions.BadRequestException

data class Series(val imdbId: String, val tmdbId: String, val name: String, val img: String)

fun checkSeriesState(state: String?):Boolean{
    if(state.isNullOrBlank()) return false
    if(state != SeriesState.PTW.state || state != SeriesState.Watched.state || state != SeriesState.Watching.state) return false
    return true
}

enum class SeriesState(val state: String) {
    PTW("PTW"),
    Watched("Watched"),
    Watching("Watching");

    companion object {
        fun fromString(string: String?) =
            when(string) {
                "PTW" -> PTW
                "Watching" -> Watching
                "Watched" -> Watched
                //null -> BadRequestException("Series State cannot be null")
                else -> throw IllegalArgumentException("Series State does not exists")
            }
    }
}