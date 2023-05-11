package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.utils.TmdbService
import pt.isel.ps.cinescope.utils.isNull

@Component
class SeriesServices(private val transactionManager: TransactionManager, private val searchServices: SearchServices) {

    fun addSeriesToList(tmdbSeriesId: Int?, listId: Int?, userId: Int?) {
        if(isNull(tmdbSeriesId) || isNull(listId) || isNull(userId)){
            throw BadRequestException("Missing information to add series to list")
        }

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

            it.seriesRepository.getSeriesFromSeriesUserData(tmdbSeriesId, userId)?: run {
                it.seriesRepository.addSeriesToSeriesUserData(userId, tmdbSeriesId, SeriesState.PTW)
            }

            if (tmdbSeriesId != null) {
                it.seriesRepository.addSeriesToList(listId, userId, tmdbSeriesId)
            } else {
                throw BadRequestException("Imdb Id cannot be null")
            }
        }

    }

    fun changeState(seriesId: Int?, state: String?, userId: Int?){//TODO return
        if(seriesId == null || userId == null){
            throw BadRequestException("Missing information to change this state")
        }
        if(!checkSeriesState(state)) throw BadRequestException("State not valid")

        transactionManager.run {
            it.seriesRepository.getSeriesFromSeriesData(seriesId) ?: run {
                val seriesDetails = searchServices.serieDetails(seriesId)
                val series = Series(seriesDetails?.externalIds?.imdb_id, seriesDetails?.serieDetails?.id, seriesDetails?.serieDetails?.name, seriesDetails?.serieDetails?.poster_path,null)
                it.seriesRepository.addSeriesToSeriesData(series)
            }

            if(it.seriesRepository.getSeriesFromSeriesUserData(seriesId, userId) != null) {
                it.seriesRepository.changeSeriesState(seriesId, userId, SeriesState.fromString(state))
            } else {
                it.seriesRepository.addSeriesToSeriesUserData(userId, seriesId, SeriesState.fromString(state))
            }

        }
    }

    fun addWatchedEpisode(tmdbSeriesId: Int?, imdbEpId: String?, epNum: Int?, seasonNum: Int?, userId: Int?){//TODO return
        if(isNull(tmdbSeriesId) || isNull(imdbEpId) || isNull(epNum) || isNull(seasonNum) || isNull(userId)){
            throw BadRequestException("Missing information to add this watched episode")
        }
        transactionManager.run {
            it.seriesRepository.getSeriesFromSeriesUserData(tmdbSeriesId, userId) ?: it.seriesRepository.addSeriesToSeriesUserData(userId, tmdbSeriesId, SeriesState.Watching)
            val episode = it.seriesRepository.getEpisodeFromEpData(imdbEpId) ?: run {

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
                val seriesUserData = it.seriesRepository.getSeriesFromSeriesUserData(tmdbSeriesId, userId)
                it.seriesRepository.addEpisodeToWatchedList(seriesUserData?.epListId, episode.imdbId, userId)
            } else {
                throw BadRequestException("Tmdb Id cannot be null")
            }
        }
    }

    fun removeWatchedEpisode(seriesId: Int?, imdbEpisodeId: String?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(imdbEpisodeId) || isNull(userId)){
            throw BadRequestException("Missing information to remove this watched episode")
        }
        transactionManager.run {
            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, userId)?.epListId
            it.seriesRepository.deleteEpisodeFromWatchedList(epListId, imdbEpisodeId)
        }
    }

    fun getWatchedEpList(seriesId: Int?, userId: Int?): List<Episode> {
        if(isNull(seriesId) || isNull(userId)){
            throw BadRequestException("Missing information to get this list")
        }
        return transactionManager.run {
            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, userId)?.epListId ?: throw NotFoundException("Series Not Found")
            it.seriesRepository.getWatchedEpList(epListId)
        }
    }

    fun getLists(userId: Int?): List<ListDetails> {
        if(isNull(userId)){
            throw BadRequestException("Missing information to get lists")
        }
        return transactionManager.run { it.seriesRepository.getLists(userId) }
    }

    fun getList(listId: Int?, userId: Int?): List<Series> {
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("Missing information to get this list")
        }
        return transactionManager.run { it.seriesRepository.getSeriesList(listId, userId) }
    }

    fun createList(userId: Int?, name: String?): Int? {
        if(isNull(userId) || isNull(name)){
            throw BadRequestException("Missing information to create list")
        }

        return transactionManager.run { it.seriesRepository.createSeriesList(userId, name)}
    }

    fun deleteSeriesFromList(listId: Int?, seriesId: Int?, userId: Int?){
        if(isNull(userId) || isNull(listId) || isNull(seriesId)){
            throw BadRequestException("Missing information to get this list")
        }
        transactionManager.run { it.seriesRepository.deleteSeriesFromList(listId, seriesId, userId) }
    }

    fun deleteList(listId: Int?, userId: Int?){//TODO return
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("Missing information to delete list")
        }

        transactionManager.run { it.seriesRepository.deleteSeriesList(listId, userId) }
    }

    fun getSeriesFromUserByState(userId: Int?, state: String?): List<Series> {
        if (isNull(userId)) {
            throw BadRequestException("User Id cannot be null")
        }

        if (!checkSeriesState(state)) {
            throw BadRequestException("State not valid")
        }

        return transactionManager.run { it.seriesRepository.getSeriesFromUserByState(userId, SeriesState.fromString(state)) }
    }
}