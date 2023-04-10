package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.repositories.TransactionManager

@Component
class MoviesServices(private val transactionManager: TransactionManager) {
    fun addMovieToList(movieId:String?, listId:Int?, userId:Int?){//TODO return

    }

    fun changeState(movieId:String?, state:String?, userId: Int?){//TODO return

    }

    fun getLists(userId: Int?){//TODO return

    }

    fun getList(listId:Int?, userId: Int?){//TODO return

    }

    fun createList(userId: Int?){//TODO return

    }

    fun deleteList(userId: Int?){//TODO return

    }
}