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
        if(isNull(tmdbSeriesId) || isNull(listId)) throw BadRequestException("Missing information to add series to list")
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.seriesRepository.getSeriesFromSeriesData(tmdbSeriesId) ?: run {
                val seriesDetails =
                    if (tmdbSeriesId != null) searchServices.serieDetails(tmdbSeriesId)
                    else throw BadRequestException("Tmdb Id cannot be null")
                val series = Series(seriesDetails?.externalIds?.imdb_id, seriesDetails?.serieDetails?.id, seriesDetails?.serieDetails?.name, seriesDetails?.serieDetails?.poster_path,null)
                it.seriesRepository.addSeriesToSeriesData(series)
            }

            it.seriesRepository.getSeriesFromSeriesUserData(tmdbSeriesId, user.id)?: run {
                it.seriesRepository.addSeriesToSeriesUserData(user.id, tmdbSeriesId, SeriesState.PTW)
            }

            if (tmdbSeriesId != null)
                it.seriesRepository.addSeriesToList(listId, user.id, tmdbSeriesId)
            else
                throw BadRequestException("Imdb Id cannot be null")
            }
        }

    fun changeState(seriesId: Int?, state: String?, bearer: String?){
        if(seriesId == null) throw BadRequestException("Missing information to change this state")

        if(!checkSeriesState(state)) throw BadRequestException("State not valid")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        if(user.id == null) throw NotFoundException("User not found")

        transactionManager.run {
            it.seriesRepository.getSeriesFromSeriesData(seriesId) ?: run {
                val seriesDetails = searchServices.serieDetails(seriesId)
                val series = Series(seriesDetails?.externalIds?.imdb_id, seriesDetails?.serieDetails?.id, seriesDetails?.serieDetails?.name, seriesDetails?.serieDetails?.poster_path,null)
                it.seriesRepository.addSeriesToSeriesData(series)
            }

            if(it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id) != null)
                it.seriesRepository.changeSeriesState(seriesId, user.id, SeriesState.fromString(state))
            else
                it.seriesRepository.addSeriesToSeriesUserData(user.id, seriesId, SeriesState.fromString(state))

        }
    }

    fun addWatchedEpisode(seriesId: Int?, epNum: Int?, seasonNum: Int?, bearer: String?){
        if(isNull(seriesId) || isNull(epNum) || isNull(seasonNum)) throw BadRequestException("Missing information to add this watched episode")
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.seriesRepository.getSeriesFromSeriesData(seriesId) ?: run {
                val seriesDetails = searchServices.serieDetails(seriesId)
                val series = Series(seriesDetails?.externalIds?.imdb_id, seriesDetails?.serieDetails?.id, seriesDetails?.serieDetails?.name, seriesDetails?.serieDetails?.poster_path,null)
                it.seriesRepository.addSeriesToSeriesData(series)
            }
            val userSerieData = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id)
            if(userSerieData == null) it.seriesRepository.addSeriesToSeriesUserData(user.id, seriesId, SeriesState.Watching)
            else if(userSerieData.state != SeriesState.Watching) it.seriesRepository.changeSeriesState(seriesId, user.id, SeriesState.Watching)

            val episode = it.seriesRepository.getEpisodeFromEpData(seriesId, seasonNum, epNum) ?: run {
                val episode =
                    if(seriesId != null && epNum != null && seasonNum != null) {
                        val epidodeDetailsOutput = searchServices.episodeDetails(seriesId, seasonNum, epNum)
                        Episode(null, epidodeDetailsOutput?.externalIds?.imdb_id, seriesId, epidodeDetailsOutput?.episodeDetails?.name, epidodeDetailsOutput?.episodeDetails?.still_path, seasonNum , epidodeDetailsOutput?.episodeDetails?.episode_number)
                    } else
                        throw BadRequestException("Tmdb Id cannot be null")

                    val id = it.seriesRepository.addEpisodeToEpData(episode)
                    return@run Episode(id, episode.epimdbId, episode.seriesId, episode.name, episode.img, episode.season, episode.episode)
                }

                if (seriesId != null) {
                    val seriesUserData = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id)
                    it.seriesRepository.addEpisodeToWatchedList(seriesUserData?.epListId, episode.epId, user.id)
                } else throw BadRequestException("Tmdb Id cannot be null")
            }
        }

    fun removeWatchedEpisode(seriesId: Int?, epNum: Int?, seasonNum: Int?, bearer: String?){
        if(isNull(seriesId) || isNull(epNum) || isNull(seasonNum)) throw BadRequestException("Missing information to remove this watched episode")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            val episode = it.seriesRepository.getEpisodeFromEpData(seriesId, seasonNum, epNum) ?: throw BadRequestException("Database - episode not found")
            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id)?.epListId
            it.seriesRepository.deleteEpisodeFromWatchedList(epListId, episode.epId)
        }
    }

    fun getWatchedEpList(seriesId: Int?, bearer: String?): List<Episode> {
        if(isNull(seriesId)) throw BadRequestException("Missing information to get this list")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run {
            val epListId = it.seriesRepository.getSeriesFromSeriesUserData(seriesId, user.id)?.epListId ?: throw NotFoundException("Series Not Found")
            it.seriesRepository.getWatchedEpList(epListId)
        }
    }

    fun getLists(bearer: String?): List<ListInfo> {

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run { it.seriesRepository.getLists(user.id) }
    }

    fun getList(listId: Int?, bearer: String?): ListDetails {
        if(isNull(listId)) throw BadRequestException("Missing information to get this list")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run {
            val list = it.seriesRepository.getSeriesList(listId, user.id)
            val info = it.seriesRepository.getSeriesListInfo(listId, user.id)
            return@run ListDetails(info, list)
        }
    }

    fun createList(bearer: String?, name: String?): Int? {
        if(isNull(name)) throw BadRequestException("Missing information to create list")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run { it.seriesRepository.createSeriesList(user.id, name)}
    }

    fun deleteSeriesFromList(listId: Int?, seriesId: Int?, bearer: String?){
        if(isNull(listId) || isNull(seriesId)) throw BadRequestException("Missing information to get this list")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run { it.seriesRepository.deleteSeriesFromList(listId, seriesId, user.id) }
    }

    fun deleteList(listId: Int?, bearer: String?){
        if(isNull(listId)) throw BadRequestException("Missing information to delete list")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.seriesRepository.deleteSeriesFromList(listId)
            it.seriesRepository.deleteSeriesList(listId, user.id)
        }
    }

    fun getSeriesFromUserByState(bearer: String?, state: String?): List<Series> {
        if (!checkSeriesState(state)) throw BadRequestException("State not valid")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        return transactionManager.run { it.seriesRepository.getSeriesFromUserByState(user.id, SeriesState.fromString(state)) }
    }

    fun removeStateFromSerie(seriesId: Int?, bearer: String?){
        if(isNull(seriesId)) throw BadRequestException("Missing information to get this list")

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or Blank")

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
        val lists = mutableListOf<ListInfo>()
        if (!listsWhereSerie.isEmpty()) listsWhereSerie.forEach { lists.add(ListInfo(it.slid, it.name)) }
        return SerieUserData(serieId, state, lists)
    }
}