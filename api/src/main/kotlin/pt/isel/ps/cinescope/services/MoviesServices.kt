package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.checkMovieState
import pt.isel.ps.cinescope.domain.checkSeriesState
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
        if(isNull(movieId) || isNull(state) || isNull(userId)){
            throw BadRequestException("Missing information to change this state")
        }
        if(!checkMovieState(state)) throw BadRequestException("State not valid")
    }

    fun getLists(userId: Int?){//TODO return
        if(isNull(userId)){
            throw BadRequestException("No user ID provided")
        }
    }

    fun getList(listId:Int?, userId: Int?){//TODO return
        if(isNull(listId) || isNull(userId) || isNull(userId)){
            throw BadRequestException("Missing information to get the list")
        }
    }

    fun createList(userId: Int?){//TODO return
        if(isNull(userId)){
            throw BadRequestException("No user ID provided")
        }
    }

    fun deleteList(listId: Int?, userId: Int?){//TODO return
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("No user ID provided")
        }
    }
}