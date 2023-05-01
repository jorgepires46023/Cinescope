package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.utils.TmdbService
import pt.isel.ps.cinescope.utils.isNull

@Component
class SeriesServices(private val transactionManager: TransactionManager, private val tmdbService: TmdbService) {

    fun addSeriesToList(imdbSeriesId: String?, tmdbSeriesId: Int?, listId: Int?, userId: Int?) {
        if(isNull(imdbSeriesId) || isNull(tmdbSeriesId) || isNull(listId) || isNull(userId)){
            throw BadRequestException("Missing information to add series to list")
        }

        transactionManager.run {
            val series = it.seriesRepository.getSeriesFromSeriesData(imdbSeriesId) ?: run {

                val seriesDetails = if (tmdbSeriesId != null) {
                    tmdbService.getSerieDetails(tmdbSeriesId)
                } else {
                    throw BadRequestException("Tmdb Id cannot be null")
                }

                val series = Series(imdbSeriesId, seriesDetails?.id, seriesDetails?.name, seriesDetails?.poster_path,null )
                it.seriesRepository.addSeriesToSeriesData(series)
                return@run series
            }

            it.seriesRepository.getSeriesFromSeriesUserData(imdbSeriesId, userId)?: run {
                it.seriesRepository.addSeriesToSeriesUserData(userId, series, SeriesState.PTW)
            }

            if (imdbSeriesId != null) {
                it.seriesRepository.addSeriesToList(listId, userId, imdbSeriesId)
            } else {
                throw BadRequestException("Imdb Id cannot be null")
            }
        }

    }

    fun changeState(seriesId: String?, state: String?, userId: Int?){//TODO return
        if(seriesId.isNullOrBlank() || userId == null){
            throw BadRequestException("Missing information to change this state")
        }
        if(!checkSeriesState(state)) throw BadRequestException("State not valid")

        transactionManager.run { it.seriesRepository.changeSeriesState(seriesId, userId, SeriesState.fromString(state)) }
    }

    fun addWatchedEpisode(tmdbSeriesId: Int?, imdbEpId: String?, epNum: Int?, seasonNum: Int?, userId: Int?){//TODO return
        if(isNull(tmdbSeriesId) || isNull(epNum) || isNull(seasonNum) || isNull(userId)){
            throw BadRequestException("Missing information to add this watched episode")
        }
        transactionManager.run {

            val episode = it.seriesRepository.getEpisodeFromEpData(imdbEpId) ?: run {

               val episode = if(tmdbSeriesId != null && epNum != null && seasonNum != null) {
                    val episodeDetails = tmdbService.getEpisodeDetails(tmdbSeriesId, epNum, seasonNum)
                    val externalEpIds = tmdbService.getEpisodeExternalId(tmdbSeriesId, epNum, seasonNum)
                    Episode(externalEpIds?.imdb_id, tmdbSeriesId, episodeDetails?.name, episodeDetails?.still_path, seasonNum ,episodeDetails?.episode_number )
                } else {
                    throw BadRequestException("Tmdb Id cannot be null")
                }

                it.seriesRepository.addEpisodeToEpData(episode)
                return@run episode
            }

            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(episode.imdbId, userId)?.epListId
            it.seriesRepository.addEpisodeToWatchedList(epListId, episode.imdbId, userId)
        }
    }

    fun removeWatchedEpisode(seriesId: String?, imdbEpisodeId: String?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(imdbEpisodeId) || isNull(userId)){
            throw BadRequestException("Missing information to remove this watched episode")
        }
        transactionManager.run {
            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, userId)?.epListId
            it.seriesRepository.deleteEpisodeFromWatchedList(epListId, imdbEpisodeId)
        }
    }

    fun getWatchedEpList(seriesId: String?, userId: Int?): List<Episode> {
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

    fun getList(listId: Int?, userId: Int?){//TODO return
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("Missing information to get this list")
        }
        transactionManager.run { it.seriesRepository.getSeriesList(listId, userId) }
    }

    fun createList(userId: Int?, name: String?){//TODO return
        if(isNull(userId) || isNull(name)){
            throw BadRequestException("Missing information to create list")
        }

        transactionManager.run { it.seriesRepository.createSeriesList(userId, name)}
    }

    fun deleteSeriesFromList(listId: Int?, seriesId: String?, userId: Int?){
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
}