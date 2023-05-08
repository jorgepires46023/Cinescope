package pt.isel.ps.cinescope.repositories.jdbi

import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.domain.Episode
import pt.isel.ps.cinescope.domain.ListDetails
import pt.isel.ps.cinescope.domain.Series
import pt.isel.ps.cinescope.domain.SeriesState
import pt.isel.ps.cinescope.repositories.SeriesRepository

class JdbiSeriesRepository(private val handle: Handle): SeriesRepository {

    override fun createSeriesList(userId: Int?, name: String?): Int?{
        return handle.createUpdate("insert into cinescope.serieslists(slid, userId, name)values(default, :userId, :name)")
            .bind("userId", userId)
            .bind("name", name)
            .executeAndReturnGeneratedKeys()
            .mapTo(Int::class.java)
            .firstOrNull()
    }

    override fun getSeriesList(id: Int?, userId: Int?): List<Series> {
        return handle.createQuery("select sd.simdbid as imdbId, sd.stmdbid as tmdbId, sd.name, sd.image as img, sud.eplid as epListId from cinescope.serieslists sls inner join cinescope.serielist sl on sl.slid = sls.slid " +
                "inner join cinescope.seriesuserdata sud on sud.simdbid = sl.simdbid inner join cinescope.seriesdata sd on sd.simdbid = sud.simdbid " +
                "where sls.slid = :listId and sls.userId = :userId")
            .bind("listId", id)
            .bind("userId", userId)
            .mapTo(Series::class.java)
            .list()
    }

    override fun deleteSeriesList(id: Int?, userId: Int?) {
        handle.createUpdate("delete from cinescope.serieslists sls where sls.slid = :listId and sls.userId = :userId")
            .bind("listId", id)
            .bind("userId", userId)
            .execute()
    }

    override fun addSeriesToList(listId: Int?, userId: Int?, seriesId: String) {
        handle.createUpdate("insert into cinescope.serielist(slid, simdbid) values(:listId, :seriesId)")
            .bind("listId", listId)
            .bind("userId", userId)
            .execute()
    }

    override fun addSeriesToSeriesData(series: Series) {
        handle.createUpdate("insert into cinescope.seriesdata(simdbid, stmdbid, name, image) " +
                "values(:imdbId, :tmdbid, :name, :image)")
            .bind("imdbid", series.imdbId)
            .bind("tmdbid", series.tmdbId)
            .bind("name", series.name)
            .bind("image", series.img)
            .execute()
    }

    override fun addSeriesToSeriesUserData(userId: Int?, series: Series, state: SeriesState) {
        handle.createUpdate("insert into cinescope.seriesuserdata(eplid, simdbid, userId, state) " +
                "values(default, :seriesId, :userId, :state)")
            .bind("seriesId", series.imdbId)
            .bind("userId", userId)
            .bind("state", state)
            .execute()
    }

    override fun getSeriesFromSeriesData(seriesId: String?): Series? {
        return handle.createQuery("select * from cinescope.seriesdata where simdbid = :seriesId")
            .bind("seriesId", seriesId)
            .mapTo(Series::class.java)
            .firstOrNull()
    }

    override fun getSeriesFromSeriesUserData(seriesId: String?, userId: Int?): Series? {
        return handle.createQuery("select * from cinescope.seriesuserdata where simdbid = :seriesId")
            .bind("seriesId", seriesId)
            .mapTo(Series::class.java)
            .firstOrNull()
    }

    override fun deleteSeriesFromList(listId: Int?, seriesId: String?, userId: Int?) {
        handle.createUpdate("delete from cinescope.serielist sl using cinescope.serieslists sls " +
                "where sls.userId = :userId and sl.simdbid = :seriesId and sl.slid = :listId and sls.slid = sl.slid")
            .bind("userId", userId)
            .bind("seriesId", seriesId)
            .bind("listId", listId)
            .execute()
    }

    override fun addEpisodeToWatchedList(eplId: Int?, epId: String?, userId: Int?) {
        handle.createUpdate("insert into cinescope.watchedepisodelist(eplid, epimdbid) values(:eplId, :epId)")
            .bind("eplid", eplId)
            .bind("epId", epId)
            .execute()
    }

    override fun addEpisodeToEpData(episode: Episode) {
        handle.createUpdate("insert into cinescope.episodedata(epimdbid, stmdbid, name, image, season, episode)" +
                "values(:epImdbId, :seriesId, :name, :image, :season, :episode)")
            .bind("epImdbId", episode.imdbId)
            .bind("seriesId", episode.seriesId)
            .bind("name", episode.name)
            .bind("image", episode.img)
            .bind("season", episode.season)
            .bind("episode", episode.episode)
            .execute()
    }

    override fun getEpisodeFromEpData(epId: String?) : Episode?{
        return handle.createQuery("select * from cinescope.episodedata ed where ed.epimdbid = :epId")
            .bind("epId", epId)
            .mapTo(Episode::class.java)
            .firstOrNull()
    }

    override fun deleteEpisodeFromWatchedList(eplId: Int?, epId: String?) {
        handle.createUpdate("delete from cinescope.watchedepisodelist wel where wel.eplid = :eplId and wel.simdbid = :epId ")
            .bind("eplId", eplId)
            .bind("epId", epId)
            .execute()
    }

    override fun changeSeriesState(seriesId: String, userId: Int, state: SeriesState) {
        handle.createUpdate("update cinescope.seriesuserdata sud set sud.state = :state " +
                "where sud.simdbid = :seriesId and sud.userid = :userId")
            .bind("state", state)
            .bind("seriesId", seriesId)
            .bind("userId", userId)
            .execute()
    }

    override fun getWatchedEpList(eplId: Int): List<Episode> {
        return handle.createQuery("select * from cinescope.watchedepisodelist wel inner join cinescope.episodedata ed" +
                "on ed.epimdbid = wel.epimdbid where eplid = :eplid ")
            .bind("eplid", eplId)
            .mapTo(Episode::class.java)
            .list()
    }

    override fun getLists(userId: Int?): List<ListDetails> {
        return handle.createQuery("select slid as id, name from cinescope.seriesLists where userid = :userId")
            .bind("userId",userId)
            .mapTo(ListDetails::class.java)
            .list()
    }


}