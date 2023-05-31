package pt.isel.ps.cinescope.repositories

import pt.isel.ps.cinescope.domain.*

interface SeriesRepository {

    fun createSeriesList(userId: Int?, name: String?): Int?

    fun getSeriesList(listId: Int?, userId: Int?): List<Series>

    fun deleteSeriesList(listId: Int?, userId: Int?)

    fun addSeriesToList(listId: Int?, userId: Int?, seriesId: Int)

    fun addSeriesToSeriesData(series: Series)

    fun addSeriesToSeriesUserData(userId: Int?, seriesId: Int?, state: SeriesState)

    fun getSeriesFromSeriesData(seriesId: Int?): Series?

    fun getSeriesFromSeriesUserData(seriesId: Int?, userId: Int?): Series?

    fun deleteSeriesFromList(listId: Int?, seriesId: Int?, userId: Int?)

    fun addEpisodeToWatchedList(eplId: Int?, epId: String?, userId: Int?)

    fun addEpisodeToEpData(episode: Episode)

    fun getEpisodeFromEpData(sId: Int?, season: Int?, epNumber: Int?): Episode?

    fun deleteEpisodeFromWatchedList(eplId: Int?, epId: String?)

    fun changeSeriesState(seriesId: Int, userId: Int, state: SeriesState)

    fun getWatchedEpList(epLId: Int): List<Episode>

    fun getLists(userId: Int?): List<ListDetails>

    fun getSeriesFromUserByState(userId: Int?, state: SeriesState?): List<Series>

    fun removeStateFromSerie(userId: Int?, serieId: Int?)

    fun deleteSeriesFromList(listId: Int?)

    fun getListsSerieIsPresent(userId: Int?, stmdbid: Int?): List<SeriesOnLists>

    fun getSerieState(userId: Int?, seriesId: Int): SeriesState?

    fun getWatchedEpisodeList(userId: Int?, stmdbid: Int?): List<Episode>
}