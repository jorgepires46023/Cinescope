package pt.isel.ps.cinescope.domain

data class Series(val imdbId: String, val name: String, val img: String)

fun checkSeriesState(state: String?):Boolean{
    if(state.isNullOrBlank()) return false
    if(state != SeriesState.PTW || state != SeriesState.Watched || state != SeriesState.Watching) return false
    return true
}

object SeriesState {
    const val PTW = "PTW"
    const val Watched = "Watched"
    const val Watching = "Watching"
}