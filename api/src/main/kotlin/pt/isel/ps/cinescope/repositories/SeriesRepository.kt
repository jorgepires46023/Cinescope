package pt.isel.ps.cinescope.repositories

import pt.isel.ps.cinescope.domain.Episode
import pt.isel.ps.cinescope.domain.ListDetails
import pt.isel.ps.cinescope.domain.Series
import pt.isel.ps.cinescope.domain.SeriesState

interface SeriesRepository {

    fun createSeriesList(userId: Int?, name: String?): Int?

    fun getSeriesList(id: Int?, userId: Int?): List<Series>

    fun deleteSeriesList(id: Int?, userId: Int?)

    fun addSeriesToList(listId: Int?, userId: Int?, seriesId: String)

    fun addSeriesToSeriesData(series: Series)

    fun addSeriesToSeriesUserData(userId: Int?, series: Series, state: SeriesState)

    fun getSeriesFromSeriesData(seriesId: String?): Series?

    fun getSeriesFromSeriesUserData(seriesId: String?, userId: Int?): Series?

    fun deleteSeriesFromList(listId: Int?, seriesId: String?, userId: Int?)

    fun addEpisodeToWatchedList(eplId: Int?, epId: String?, userId: Int?)

    fun addEpisodeToEpData(episode: Episode)

    fun getEpisodeFromEpData(epId: String?): Episode?

    fun deleteEpisodeFromWatchedList(eplId: Int?, epId: String?)

    fun changeSeriesState(seriesId: String, userId: Int, state: SeriesState)

    fun getWatchedEpList(epLId: Int): List<Episode>

    fun getLists(userId: Int?): List<ListDetails>

    fun getSeriesFromUserByState(userId: Int?, state: SeriesState?): List<Series>
}