package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.controllers.TokenProcessor
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.utils.isNull

@Component
class SeriesServices(
    private val transactionManager: TransactionManager,
    private val searchServices: SearchServices,
    private val tokenProcessor: TokenProcessor
    ) {

    fun addSeriesToList(tmdbSeriesId: Int?, listId: Int?, bearer: String?) {
        if(isNull(tmdbSeriesId) || isNull(listId)){
            throw BadRequestException("Missing information to add series to list")
        }
        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            val series = it.seriesRepository.getSeriesFromSeriesData(tmdbSeriesId) ?: run {

                val seriesDetails = if (tmdbSeriesId != null) {
                    searchServices.serieDetails(tmdbSeriesId)
                } else {
                    throw BadRequestException("Tmdb Id cannot be null")
                }
                val series = Series(seriesDetails?.externalIds?.imdb_id, seriesDetails?.serieDetails?.id, seriesDetails?.serieDetails?.name, seriesDetails?.serieDetails?.poster_path,null)
                it.seriesRepository.addSeriesToSeriesData(series)
                return@run series
            }

            it.seriesRepository.getSeriesFromSeriesUserData(tmdbSeriesId, user.id)?: run {
                it.seriesRepository.addSeriesToSeriesUserData(user.id, tmdbSeriesId, SeriesState.PTW)
            }

            if (tmdbSeriesId != null) {
                it.seriesRepository.addSeriesToList(listId, user.id, tmdbSeriesId)
            } else {
                throw BadRequestException("Imdb Id cannot be null")
            }
        }

    }

    fun changeState(seriesId: Int?, state: String?, bearer: String?){//TODO return
        if(seriesId == null){
            throw BadRequestException("Missing information to change this state")
        }

        if(!checkSeriesState(state)) throw BadRequestException("State not valid")

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        if(user.id == null) throw NotFoundException("User not found")

        transactionManager.run {
            it.seriesRepository.getSeriesFromSeriesData(seriesId) ?: run {
                val seriesDetails = searchServices.serieDetails(seriesId)
                val series = Series(seriesDetails?.externalIds?.imdb_id, seriesDetails?.serieDetails?.id, seriesDetails?.serieDetails?.name, seriesDetails?.serieDetails?.poster_path,null)
                it.seriesRepository.addSeriesToSeriesData(series)
            }

            if(it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id) != null) {
                it.seriesRepository.changeSeriesState(seriesId, user.id, SeriesState.fromString(state))
            } else {
                it.seriesRepository.addSeriesToSeriesUserData(user.id, seriesId, SeriesState.fromString(state))
            }

        }
    }

    //TODO dont allow same ep per user
    fun addWatchedEpisode(tmdbSeriesId: Int?, epNum: Int?, seasonNum: Int?, bearer: String?){//TODO return
        if(isNull(tmdbSeriesId) || isNull(epNum) || isNull(seasonNum)) throw BadRequestException("Missing information to add this watched episode")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")


        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.seriesRepository.getSeriesFromSeriesUserData(tmdbSeriesId, user.id) ?: it.seriesRepository.addSeriesToSeriesUserData(user.id, tmdbSeriesId, SeriesState.Watching)
            val episode = it.seriesRepository.getEpisodeFromEpData(tmdbSeriesId, seasonNum, epNum) ?: run {

               val episode = if(tmdbSeriesId != null && epNum != null && seasonNum != null) {
                   val epidodeDetailsOutput = searchServices.episodeDetails(tmdbSeriesId, seasonNum, epNum)
                    Episode(epidodeDetailsOutput?.externalIds?.imdb_id, tmdbSeriesId, epidodeDetailsOutput?.episodeDetails?.name, epidodeDetailsOutput?.episodeDetails?.still_path, seasonNum , epidodeDetailsOutput?.episodeDetails?.episode_number)
                } else {
                    throw BadRequestException("Tmdb Id cannot be null")
                }

                it.seriesRepository.addEpisodeToEpData(episode)
                return@run episode
            }

            if (tmdbSeriesId != null) {
                val seriesUserData = it.seriesRepository.getSeriesFromSeriesUserData(tmdbSeriesId, user.id)
                it.seriesRepository.addEpisodeToWatchedList(seriesUserData?.epListId, episode.imdbId, user.id)
            } else {
                throw BadRequestException("Tmdb Id cannot be null")
            }
        }
    }

    fun removeWatchedEpisode(seriesId: Int?, epNum: Int?, seasonNum: Int?, bearer: String?){//TODO return
        if(isNull(seriesId) || isNull(epNum) || isNull(seasonNum)) throw BadRequestException("Missing information to remove this watched episode")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            val episode = it.seriesRepository.getEpisodeFromEpData(seriesId, seasonNum, epNum) ?: throw BadRequestException("Database - episode not found")
            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id)?.epListId
            it.seriesRepository.deleteEpisodeFromWatchedList(epListId, episode.imdbId)
        }
    }

    fun getWatchedEpList(seriesId: Int?, bearer: String?): List<Episode> {
        if(isNull(seriesId)){
            throw BadRequestException("Missing information to get this list")
        }

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run {
            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id)?.epListId ?: throw NotFoundException("Series Not Found")
            it.seriesRepository.getWatchedEpList(epListId)
        }
    }

    fun getLists(bearer: String?): List<ListDetails> {

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run { it.seriesRepository.getLists(user.id) }
    }

    fun getList(listId: Int?, bearer: String?): List<Series> {
        if(isNull(listId)){
            throw BadRequestException("Missing information to get this list")
        }

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run { it.seriesRepository.getSeriesList(listId, user.id) }
    }

    fun createList(bearer: String?, name: String?): Int? {
        if(isNull(name)){
            throw BadRequestException("Missing information to create list")
        }

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run { it.seriesRepository.createSeriesList(user.id, name)}
    }

    fun deleteSeriesFromList(listId: Int?, seriesId: Int?, bearer: String?){
        if(isNull(listId) || isNull(seriesId)){
            throw BadRequestException("Missing information to get this list")
        }

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run { it.seriesRepository.deleteSeriesFromList(listId, seriesId, user.id) }
    }

    fun deleteList(listId: Int?, bearer: String?){//TODO return
        if(isNull(listId)){
            throw BadRequestException("Missing information to delete list")
        }

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.seriesRepository.deleteSeriesFromList(listId)
            it.seriesRepository.deleteSeriesList(listId, user.id)
        }
    }

    fun getSeriesFromUserByState(bearer: String?, state: String?): List<Series> {
        if (!checkSeriesState(state)) {
            throw BadRequestException("State not valid")
        }

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run { it.seriesRepository.getSeriesFromUserByState(user.id, SeriesState.fromString(state)) }
    }

    fun removeStateFromSerie(seriesId: Int?, bearer: String?){
        if(isNull(seriesId)){
            throw BadRequestException("Missing information to get this list")
        }

        if(bearer.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or Blank")
        }

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run {
            it.seriesRepository.removeStateFromSerie(user.id, seriesId)
        }
    }

    fun getSerieUserData(serieId: Int?, bearer: String?): SerieUserData?{
        if (serieId == null) throw BadRequestException("serieId cant be null")
        if (bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")
        val state = transactionManager.run{
            return@run it.seriesRepository.getSerieState(user.id, serieId)
        } ?: return SerieUserData(serieId, null, null)
        val listsWhereSerie = transactionManager.run {
            return@run it.seriesRepository.getListsSerieIsPresent(user.id, serieId)
        }
//        val episodeData = transactionManager.run{
//            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(serieId, user.id)?.epListId ?: throw NotFoundException("Series Not Found")
//            return@run it.seriesRepository.getWatchedEpList(epListId)
//        }
//      if we want to send all info in one request
        val lists = mutableListOf<ListDetails>()
        if (!listsWhereSerie.isEmpty()) listsWhereSerie.forEach { lists.add(ListDetails(it.slid, it.name)) }
        return SerieUserData(serieId, state, lists)
    }
}