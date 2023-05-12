package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.controllers.TokenProcessor
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.utils.TmdbService
import pt.isel.ps.cinescope.utils.isNull

@Component
class MoviesServices(
    private val transactionManager: TransactionManager,
    private val tmdbService: TmdbService,
    private val tokenProcessor: TokenProcessor
    ) {

    fun createList(bearer: String?, name: String?): Int? {
        if(name.isNullOrBlank()){
            throw BadRequestException("No name for the List provided")
        }
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")
        return transactionManager.run {
            it.moviesRepository.createList(user.id, name)
        }
    }

    fun getList(listId:Int?, bearer: String?): List<Movie> {
        if(isNull(listId)){
            throw BadRequestException("Missing information to get the list")
        }
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")
        return transactionManager.run { it.moviesRepository.getMoviesListById(listId, user.id) }
    }

    fun addMovieToList(tmdbMovieId: Int?, listId:Int?, bearer: String?){//TODO return
        if(tmdbMovieId == null || listId  == null){
            throw BadRequestException("Missing information to add movie to list")
        }
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            val movie = it.moviesRepository.getMovieFromMovieData(tmdbMovieId)?: run {

                val movieDetails = tmdbService.getMovieDetails(tmdbMovieId) ?: throw BadRequestException("Tmdb Id cannot be null")
                val movie = Movie(movieDetails.imdb_id, movieDetails.id, movieDetails.title, movieDetails.poster_path)
                it.moviesRepository.addMovieToMovieData(movie)
                return@run movie
            }

            it.moviesRepository.getMovieFromMovieUserData(tmdbMovieId, user.id) ?: run {
                it.moviesRepository.addMovieToUserData(user.id, tmdbMovieId, MovieState.PTW)
            }

            it.moviesRepository.addMovieToList(listId, movie)

        }
    }

    fun deleteMovieFromList(listId: Int?, movieId: Int?, bearer: String?){
        if(isNull(listId) || isNull(movieId)){
            throw BadRequestException("Missing information to delete movie from this list")
        }
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run { it.moviesRepository.deleteMovieFromList(listId, movieId, user.id) }
    }

    fun deleteList(listId: Int?, bearer: String?){//TODO return
        if(isNull(listId)){
            throw BadRequestException("No user Id or list Id provided")
        }
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.moviesRepository.deleteMoviesFromList(listId)
            it.moviesRepository.deleteMoviesList(listId, user.id)
        }
    }

    fun changeState(movieId: Int?, state: String?, bearer: String?){//TODO return
        if(movieId == null || isNull(state)){
            throw BadRequestException("Missing information to change this state")
        }
        if(!checkMovieState(state) || state.isNullOrBlank()) throw BadRequestException("State not valid")
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")
        transactionManager.run {
            it.moviesRepository.getMovieFromMovieData(movieId) ?: run {
                val movieDetails = tmdbService.getMovieDetails(movieId)
                val movie = Movie(movieDetails?.imdb_id, movieDetails?.id, movieDetails?.title, movieDetails?.poster_path)
                it.moviesRepository.addMovieToMovieData(movie)
            }
            if(it.moviesRepository.getMovieFromMovieUserData(movieId, user.id) != null)
                it.moviesRepository.changeState(movieId, user.id, MovieState.fromString(state))
            else
                it.moviesRepository.addMovieToUserData(user.id, movieId, MovieState.fromString(state))
             }
    }

    fun getLists(bearer: String?): List<ListDetails>{
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")
        return transactionManager.run { it.moviesRepository.getLists(user.id) }
    }

    fun getMoviesFromUserByState(bearer: String?, state: String?): List<Movie> {
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        if (!checkMovieState(state)){
            throw BadRequestException("State not valid")
        }

        return transactionManager.run { it.moviesRepository.getMoviesFromUserByState(user.id, MovieState.fromString(state)) }
    }

    fun deleteStateFromMovie(movieId: Int?, bearer: String?){
        if(movieId == null) throw BadRequestException("movieid cant be null")
        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.moviesRepository.removeStateFromMovies(movieId, user.id)
        }
    }
}