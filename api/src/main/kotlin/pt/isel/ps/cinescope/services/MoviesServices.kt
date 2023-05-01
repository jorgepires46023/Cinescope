package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.Movie
import pt.isel.ps.cinescope.domain.MovieState
import pt.isel.ps.cinescope.domain.checkMovieState
import pt.isel.ps.cinescope.domain.checkSeriesState
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.utils.TmdbService
import pt.isel.ps.cinescope.utils.isNull

@Component
class MoviesServices(private val transactionManager: TransactionManager, private val tmdbService: TmdbService) {
    fun createList(userId: Int?){//TODO return
        val name = "List name"  //TODO add name as parameter
        if(isNull(userId)){
            throw BadRequestException("No user ID provided")
        }
        transactionManager.run { it.moviesRepository.createList(userId, name) }
    }
    fun getList(listId:Int?, userId: Int?){//TODO return
        if(isNull(listId) || isNull(userId) || isNull(userId)){
            throw BadRequestException("Missing information to get the list")
        }
        return transactionManager.run { it.moviesRepository.getMoviesListById(listId, userId) }
    }
    fun addMovieToList(tmdbMovieId: Int?, imdbMovieId: String?, listId:Int?, userId:Int?){//TODO return
        if(isNull(tmdbMovieId) || isNull(imdbMovieId) || isNull(listId) || isNull(userId)){
            throw BadRequestException("Missing information to add movie to list")
        }

        transactionManager.run {
            val movie = it.moviesRepository.getMovieFromMovieData(imdbMovieId)?: run {

                val movieDetails = if (tmdbMovieId != null) {
                    tmdbService.getMovieDetails(tmdbMovieId)
                } else {
                    throw BadRequestException("Tmdb Id cannot be null")
                }
                val movie = Movie(movieDetails?.imdb_id, movieDetails?.id, movieDetails?.title, movieDetails?.poster_path)
                it.moviesRepository.addMovieToMovieData(movie)
                return@run movie
            }

            it.moviesRepository.getMovieFromMovieUserData(imdbMovieId, userId) ?: run {
                it.moviesRepository.addMovieToUserData(userId, movie, MovieState.PTW)
            }

            it.moviesRepository.addMovieToList(listId, userId, movie)

        }
    }
    fun deleteMovieFromList(listId: Int?, movieId: String?, userId: Int?){
        if(isNull(listId) || isNull(movieId) || isNull(userId)){
            throw BadRequestException("Missing information to delete movie from this list")
        }

        transactionManager.run { it.moviesRepository.deleteMovieFromList(listId, movieId, userId) }
    }
    fun deleteList(listId: Int?, userId: Int?){//TODO return
        if(isNull(userId) || isNull(listId)){
            throw BadRequestException("No user ID provided")
        }
        transactionManager.run { it.moviesRepository.deleteMoviesList(listId, userId)}
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

        transactionManager.run { it.moviesRepository.getLists(userId) }
    }
}