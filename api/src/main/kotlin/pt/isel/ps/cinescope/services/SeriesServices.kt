package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.SeriesState
import pt.isel.ps.cinescope.domain.checkSeriesState
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.utils.isNull

@Component
class SeriesServices(private val transactionManager: TransactionManager) {

    fun addSeriesToList(seriesId: String?, listId: Int?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(listId) || isNull(userId)){
            throw BadRequestException("Missing information to add series to list")
        }
    }

    fun changeState(seriesId: String?, state: String?, userId: Int?){//TODO return
        if(seriesId.isNullOrBlank() || userId == null){
            throw BadRequestException("Missing information to change this state")
        }
        if(!checkSeriesState(state)) throw BadRequestException("State not valid")

        transactionManager.run { it.seriesRepository.changeSeriesState(seriesId, userId, SeriesState.fromString(state)) }
    }

    fun addWatchedEpisode(seriesId: String?, episodeId: String?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(episodeId) || isNull(userId)){
            throw BadRequestException("Missing information to add this watched episode")
        }
        //TODO transactionManager.run { it.seriesRepository.addEpisodeToWatchedList() }
    }

    fun removeWatchedEpisode(seriesId: String?, episodeId: String?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(episodeId) || isNull(userId)){
            throw BadRequestException("Missing information to remove this watched episode")
        }
        //TODO transactionManager.run { it.seriesRepository.deleteEpisodeFromWatchedList(seriesId, episodeId) }
    }

    fun getWatchedEpList(seriesId: String?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(userId)){
            throw BadRequestException("Missing information to get this list")
        }
        //transactionManager.run { it.seriesRepository.getSeriesList(seriesId, userId) }
    }

    fun getLists(userId: Int?){//TODO return
        if(isNull(userId)){
            throw BadRequestException("Missing information to get lists")
        }
        //TODO ? transactionManager.run { it.seriesRepository. }
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

        transactionManager.run { it.seriesRepository.createSeriesList(userId, name) }
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