package pt.isel.ps.cinescope.repositories.database.jdbi

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.repositories.database.SeriesRepository

class JdbiSeriesRepository(private val handle: Handle): SeriesRepository {

    override fun createSeriesList(userId: Int?, name: String?): Int?{
        return handle.createUpdate("insert into cinescope.serieslists(slid, userId, name)values(default, :userId, :name)")
            .bind("userId", userId)
            .bind("name", name)
            .executeAndReturnGeneratedKeys()
            .mapTo(Int::class.java)
            .firstOrNull()
    }

    override fun getSeriesList(listId: Int?, userId: Int?): List<Series> {
        return handle.createQuery("select sd.simdbid as imdbId, sd.stmdbid as tmdbId, sd.name, sd.image as img, sud.eplid as epListId, sud.state " +
                "from cinescope.serieslists sls inner join cinescope.serielist sl on sl.slid = sls.slid " +
                "inner join cinescope.seriesuserdata sud on sud.stmdbid = sl.stmdbid " +
                "inner join cinescope.seriesdata sd on (sd.stmdbid = sud.stmdbid and sud.userid =:userId)" +
                "where sls.slid = :listId")
            .bind("listId", listId)
            .bind("userId", userId)
            .mapTo(Series::class.java)
            .list()
    }

    override fun getSeriesListInfo(listId: Int?, userId: Int?): ListInfo? {
        return handle.createQuery("select slid as id, name from cinescope.serieslists where slid = :id and userid = :userId")
            .bind("id", listId)
            .bind("userId", userId)
            .mapTo(ListInfo::class.java)
            .firstOrNull()
    }

    override fun deleteSeriesList(listId: Int?, userId: Int?) {
        handle.createUpdate("delete from cinescope.serieslists sls where sls.slid = :listId and sls.userId = :userId")
            .bind("listId", listId)
            .bind("userId", userId)
            .execute()
    }

    override fun addSeriesToList(listId: Int?, userId: Int?, seriesId: Int) {
        handle.createUpdate("insert into cinescope.serielist(slid, stmdbid) values(:listId, :seriesId)")
            .bind("listId", listId)
            .bind("seriesId", seriesId)
            .execute()
    }

    override fun addSeriesToSeriesData(series: Series) {
        handle.createUpdate("insert into cinescope.seriesdata(simdbid, stmdbid, name, image) " +
                "values(:imdbId, :tmdbId, :name, :image)")
            .bind("imdbId", series.imdbId)
            .bind("tmdbId", series.tmdbId)
            .bind("name", series.name)
            .bind("image", series.img)
            .execute()
    }

    override fun addSeriesToSeriesUserData(userId: Int?, seriesId: Int?, state: SeriesState) {
        handle.createUpdate("insert into cinescope.seriesuserdata(eplid, stmdbid, userId, state) " +
                "values(default, :seriesId, :userId, :state)")
            .bind("seriesId", seriesId)
            .bind("userId", userId)
            .bind("state", state)
            .execute()
    }

    override fun getSeriesFromSeriesData(seriesId: Int?): Series? {
        return handle.createQuery("select simdbid as imdbId, stmdbid as tmdbId, name, image as img from cinescope.seriesdata where stmdbid = :seriesId")
            .bind("seriesId", seriesId)
            .mapTo(Series::class.java)
            .firstOrNull()
    }

    override fun getSeriesFromSeriesUserData(seriesId: Int?, userId: Int?): Series? {
        return handle.createQuery("select state, eplid as epListId from cinescope.seriesuserdata where stmdbid = :seriesId")
            .bind("seriesId", seriesId)
            .mapTo(Series::class.java)
            .firstOrNull()
    }

    override fun deleteSeriesFromList(listId: Int?, seriesId: Int?, userId: Int?) {
        handle.createUpdate("delete from cinescope.serielist sl using cinescope.serieslists sls " +
                "where sls.userId = :userId and sl.stmdbid = :seriesId and sl.slid = :listId and sls.slid = sl.slid")
            .bind("userId", userId)
            .bind("seriesId", seriesId)
            .bind("listId", listId)
            .execute()
    }

    override fun addEpisodeToWatchedList(eplId: Int?, epId: Int?, userId: Int?) {
        handle.createUpdate("insert into cinescope.watchedepisodelist(eplid, epid) values(:eplId, :epId)")
            .bind("eplId", eplId)
            .bind("epId", epId)
            .execute()
    }

    override fun addEpisodeToEpData(episode: Episode): Int? {
        return handle.createUpdate("insert into cinescope.episodesdata(epid, epimdbid, stmdbid, name, image, season, episode)" +
                "values(default, :epImdbId, :seriesId, :name, :image, :season, :episode)")
            .bind("epImdbId", episode.epimdbId)
            .bind("seriesId", episode.seriesId)
            .bind("name", episode.name)
            .bind("image", episode.img)
            .bind("season", episode.season)
            .bind("episode", episode.episode)
            .executeAndReturnGeneratedKeys()
            .mapTo(Int::class.java)
            .firstOrNull()
    }

    override fun getEpisodeFromEpData(sId: Int?, season: Int?, epNumber: Int?) : Episode?{
        return handle.createQuery("select epId, epimdbid as epimdbId, stmdbid as seriesId, name, image as img, season, episode from cinescope.episodesdata ed " +
                "where ed.stmdbid = :sId and ed.season = :season and ed.episode = :epNumber")
            .bind("sId", sId)
            .bind("season", season)
            .bind("epNumber", epNumber)
            .mapTo(Episode::class.java)
            .firstOrNull()
    }

    override fun deleteEpisodeFromWatchedList(eplId: Int?, epId: Int?) {
        handle.createUpdate("delete from cinescope.watchedepisodelist wel where wel.eplid = :eplId and wel.epid = :epId ")
            .bind("eplId", eplId)
            .bind("epId", epId)
            .execute()
    }

    override fun changeSeriesState(seriesId: Int?, userId: Int?, state: SeriesState) {
        handle.createUpdate("update cinescope.seriesuserdata set state = :state " +
                "where stmdbid = :seriesId and userid = :userId")
            .bind("state", state)
            .bind("seriesId", seriesId)
            .bind("userId", userId)
            .execute()
    }

    override fun getWatchedEpList(epLId: Int): List<Episode> {
        return handle.createQuery("select wel.epid as epId, ed.stmdbid as seriesId, ed.epimdbid, name, image as img, season, episode " +
                    "from cinescope.watchedepisodelist wel inner join cinescope.episodesdata ed " +
                    "on ed.epid = wel.epid where eplid = :eplid" +
                    " order by episode asc")
            .bind("eplid", epLId)
            .mapTo(Episode::class.java)
            .list()
    }

    override fun getLists(userId: Int?): List<ListInfo> {
        val q = handle.createQuery("select slid as id, name from cinescope.seriesLists where userid = :userId")
            .bind("userId",userId)
            .mapTo(ListInfo::class.java)
        return if (q.firstOrNull() == null) emptyList()
        else q.list()
    }

    override fun getSeriesFromUserByState(userId: Int?, state: SeriesState?): List<Series> {
        return  handle.createQuery(" select sd.simdbid as imdbId, sd.stmdbid as tmdbId, sd.name, sd.image as img, sud.eplid as epListId, sud.state " +
                "from cinescope.seriesuserdata sud inner join cinescope.seriesdata sd on sd.stmdbid = sud.stmdbid " +
                "where sud.state = :state and sud.userId = :userId ")
            .bind("userId", userId)
            .bind("state", state)
            .mapTo(Series::class.java)
            .list()
    }

    override fun removeStateFromSerie(userId: Int?, serieId: Int?) {
        handle.createUpdate("call cinescope.removestatefromserie(:serieId, :userId)")
            .bind("serieId", serieId)
            .bind("userId", userId)
            .execute()
    }

    override fun deleteSeriesFromList(listId: Int?) {
        handle.createUpdate("delete from cinescope.serielist where slid = :slid")
            .bind("slid", listId)
            .execute()
    }

    override fun getListsSerieIsPresent(userId: Int?, stmdbid: Int?): List<SeriesOnLists> {
        return handle.createQuery("select * from cinescope.seriesonlists where userid = :userid and stmdbid = :stmdbid")
            .bind("userid", userId)
            .bind("stmdbid", stmdbid)
            .mapTo(SeriesOnLists::class.java)
            .list()
    }

    override fun getSerieState(userId: Int?, seriesId: Int): SeriesState? {
        return handle.createQuery("select state from cinescope.seriesuserdata where userid = :userid and stmdbid = :stmdbid")
            .bind("userid", userId)
            .bind("stmdbid", seriesId)
            .mapTo<SeriesState>()
            .firstOrNull()
    }
}