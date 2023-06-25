package pt.isel.ps.cinescope.domain

data class Series(val imdbId: String?, val tmdbId: Int?, val name: String?, val img: String?, val epListId: Int?, val state: SeriesState? = null)
data class SerieUserData(val id: Int, val state: SeriesState? = null, val lists: List<ListInfo>?/*, val episodes: List<Episode>*/)
data class SeriesOnLists(val slid: Int, val userId: Int, val name: String, val stmdbid: Int, val state: String?/*, val episodeList: Int*/)
fun checkSeriesState(state: String?):Boolean{
    if(state.isNullOrBlank()) return false
    if(state == SeriesState.PTW.state || state == SeriesState.Watched.state || state == SeriesState.Watching.state) return true
    return false
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