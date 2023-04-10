package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
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
        if(isNull(seriesId) || isNull(userId)){
            throw BadRequestException("Missing information to change this state")
        }
        if(!checkSeriesState(state)) throw BadRequestException("State not valid")
    }

    fun addWatchedEpisode(seriesId: String?, episodeId: String?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(episodeId) || isNull(userId)){
            throw BadRequestException("Missing information to add this watched episode")
        }
    }

    fun removeWatchedEpisode(seriesId: String?, episodeId: String?, userId: Int?){//TODO return
        if(isNull(seriesId) || isNull(episodeId) || isNull(userId)){
            throw BadRequestException("Missing information to remove this watched episode")
        }
    }

    fun getWatchedEpList(listId: Int?, userId: Int?){//TODO return
        if(isNull(listId) || isNull(userId)){
            throw BadRequestException("Missing information to get this list")
        }
    }

    fun getLists(userId: Int?){//TODO return
        if(isNull(userId)){
            throw BadRequestException("Missing information to get lists")
        }
    }

    fun getList(listId: Int?, userId: Int?){//TODO return
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("Missing information to get this list")
        }
    }

    fun createList(userId: Int?){//TODO return
        if(isNull(userId)){
            throw BadRequestException("Missing information to create list")
        }
    }

    fun deleteList(listId: Int?, userId: Int?){//TODO return
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("Missing information to delete list")
        }
    }
}