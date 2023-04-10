package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.repositories.TransactionManager

@Component
class SeriesServices(private val transactionManager: TransactionManager) {

    fun addSeriesToList(seriesId: String?, listId: Int?, userId: Int?){//TODO return

    }

    fun changeState(seriesId: String?, state: String?, userId: Int?){//TODO return

    }

    fun addWatchedEpisode(seriesId: String?, episodeId: String?, userId: Int?){//TODO return

    }

    fun removeWatchedEpisode(seriesId: String?, episodeId: String?, userId: Int?){//TODO return

    }

    fun getWatchedEpList(listId: Int?, userId: Int?){//TODO return

    }

    fun getLists(userId: Int?){//TODO return

    }

    fun getList(listId: Int?, userId: Int?){//TODO return

    }

    fun createList(userId: Int?){//TODO return

    }

    fun deleteList(userId: Int?){//TODO return

    }
}