package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.controllers.TokenProcessor
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.repositories.database.TransactionManager
import pt.isel.ps.cinescope.repositories.tmdb.TmdbRepository
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.utils.isNull

@Component
class MoviesServices(
    private val transactionManager: TransactionManager,
    private val tmdbRepository: TmdbRepository,
    private val tokenProcessor: TokenProcessor
    ) {

    fun createList(token: String?, name: String?): Int? {
        if(name.isNullOrBlank()) throw BadRequestException("No name for the List provided")
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        return transactionManager.run {
            it.moviesRepository.createList(user.id, name)
        }
    }

    fun getList(listId:Int?, token: String?): ListDetails {
        if(isNull(listId)) throw BadRequestException("Missing information to get the list")
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        return transactionManager.run {
            val list = it.moviesRepository.getMoviesListById(listId, user.id)
            val info = it.moviesRepository.getMovieListInfo(listId, user.id)
            return@run ListDetails(info, list)
        }
    }

    fun addMovieToList(tmdbMovieId: Int?, listId:Int?, token: String?){
        if(tmdbMovieId == null || listId  == null) throw BadRequestException("Missing information to add movie to list")
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        transactionManager.run {
            val movie = it.moviesRepository.getMovieFromMovieData(tmdbMovieId) ?: run {
                val movieDetails = tmdbRepository.getMovieDetails(tmdbMovieId) ?: throw BadRequestException("Tmdb Id cannot be null")
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

    fun deleteMovieFromList(listId: Int?, movieId: Int?, token: String?){
        if(isNull(listId) || isNull(movieId)) throw BadRequestException("Missing information to delete movie from this list")
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        transactionManager.run { it.moviesRepository.deleteMovieFromList(listId, movieId, user.id) }
    }

    fun deleteList(listId: Int?, token: String?){
        if(isNull(listId)) throw BadRequestException("No user Id or list Id provided")
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")

        transactionManager.run {
            it.moviesRepository.deleteMoviesFromList(listId)
            it.moviesRepository.deleteMoviesList(listId, user.id)
        }
    }

    fun changeState(movieId: Int?, state: String?, token: String?){
        if(movieId == null || state.isNullOrBlank()) throw BadRequestException("Missing information to change this state")
        if(!checkMovieState(state)) throw BadRequestException("State not valid")
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        transactionManager.run {
            it.moviesRepository.getMovieFromMovieData(movieId) ?: run {
                val movieDetails = tmdbRepository.getMovieDetails(movieId)
                val movie = Movie(movieDetails?.imdb_id, movieDetails?.id, movieDetails?.title, movieDetails?.poster_path)
                it.moviesRepository.addMovieToMovieData(movie)
            }
            if(it.moviesRepository.getMovieFromMovieUserData(movieId, user.id) != null)
                it.moviesRepository.changeState(movieId, user.id, MovieState.fromString(state))
            else
                it.moviesRepository.addMovieToUserData(user.id, movieId, MovieState.fromString(state))
        }
    }

    fun getLists(token: String?): List<ListInfo>{
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        return transactionManager.run { it.moviesRepository.getLists(user.id) }
    }

    fun getMoviesFromUserByState(token: String?, state: String?): List<Movie> {
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        if (!checkMovieState(state)) throw BadRequestException("State not valid")
        return transactionManager.run { it.moviesRepository.getMoviesFromUserByState(user.id, MovieState.fromString(state)) }
    }

    fun deleteStateFromMovie(movieId: Int?, token: String?){
        if(movieId == null) throw BadRequestException("movieid cant be null")
        if(token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        transactionManager.run {
            it.moviesRepository.removeStateFromMovies(movieId, user.id)
        }
    }

    fun getMovieUserData(movieId: Int?, token: String?): MovieUserData? {
        if (movieId == null) throw BadRequestException("movieid cant be null")
        if (token.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(token) ?: throw NotFoundException("User not found")
        val state = transactionManager.run {
            return@run it.moviesRepository.getMovieState(user.id, movieId) } ?: return MovieUserData(movieId, null, null)
        val res = transactionManager.run {
            return@run it.moviesRepository.getMovieUserData(user.id, movieId)
        }
        val lists = mutableListOf<ListInfo>()
        if (!res.isEmpty()) res.forEach { lists.add(ListInfo(it.mlid, it.name)) }
        return MovieUserData(movieId, state, lists)
    }
}