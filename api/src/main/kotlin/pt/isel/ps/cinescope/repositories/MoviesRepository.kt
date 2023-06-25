package pt.isel.ps.cinescope.repositories

import pt.isel.ps.cinescope.domain.ListInfo
import pt.isel.ps.cinescope.domain.Movie
import pt.isel.ps.cinescope.domain.MovieOnLists
import pt.isel.ps.cinescope.domain.MovieState

interface MoviesRepository {

    fun createList(userId: Int?, name: String): Int?

    fun getMoviesListById(id: Int?, userId: Int?): List<Movie>

    fun getMovieListInfo(id: Int?, userId: Int?): ListInfo

    fun addMovieToList(listId: Int?, movie: Movie)

    fun deleteMoviesList(listId: Int?, userId: Int?)

    fun addMovieToUserData(userId: Int?, movieId: Int, state: MovieState)

    fun addMovieToMovieData(movie: Movie)

    fun getMovieFromMovieData(movieId: Int?): Movie?

    fun getMovieFromMovieUserData(movieId: Int?, userId: Int?): Movie?

    fun changeState(movieId: Int?,userId: Int?, state: MovieState)

    fun deleteMovieFromList(listId: Int?, movieId: Int?, userId: Int?)

    fun getLists(userId: Int?) :List<ListInfo>

    fun getMoviesFromUserByState(userId: Int?, state: MovieState?): List<Movie>

    fun deleteMovieFromUserData(movieId: Int?, userId: Int?)

    fun removeStateFromMovies(movieId: Int, userId: Int?)

    fun deleteMoviesFromList(listId: Int?)

    fun getMovieUserData(userId: Int?, mtmdbid: Int?): List<MovieOnLists>

    fun getMovieState(userId: Int?, mtmdbid: Int?): MovieState?

}