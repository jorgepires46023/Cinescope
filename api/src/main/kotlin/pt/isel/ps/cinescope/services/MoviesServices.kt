package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.utils.isNull

@Component
class MoviesServices(private val transactionManager: TransactionManager) {
    fun addMovieToList(movieId:String?, listId:Int?, userId:Int?){//TODO return
        if(isNull(movieId) || isNull(listId) || isNull(userId)){
            throw BadRequestException("Missing information to add movie to list")
        }
        //TODO Wait for Repository
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