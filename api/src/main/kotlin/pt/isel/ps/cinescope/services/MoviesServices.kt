package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.MovieState
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
        transactionManager.run {
            val movie = it.moviesRepository.getMovieFromMovieData(movieId) //?: it.moviesRepository.addMovieToMovieData(movieId)
            //TODO
            //Fetch para API caso nao exista para obter dados
            //Adicionar ao Movies Data e à lista
        }
        //TODO Wait for Repository
    }

    fun changeState(movieId:String?, state:String?, userId: Int?){//TODO return
        if(isNull(movieId) || isNull(state) || isNull(userId)){
            throw BadRequestException("Missing information to change this state")
        }
        if(!checkMovieState(state) || state.isNullOrBlank()) throw BadRequestException("State not valid")

        transactionManager.run { it.moviesRepository.changeState(movieId, userId, MovieState.fromString(state)) }
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
        return transactionManager.run { it.moviesRepository.getMoviesListById(listId, userId) }
    }

    fun createList(userId: Int?){//TODO return
        val name = "List name"  //TODO add name as parameter
        if(isNull(userId)){
            throw BadRequestException("No user ID provided")
        }
        transactionManager.run { it.moviesRepository.createList(userId, name) }
    }

    fun deleteList(listId: Int?, userId: Int?){//TODO return
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("No user ID provided")
        }
        transactionManager.run { it.moviesRepository.deleteMoviesList(listId, userId)}
    }
}