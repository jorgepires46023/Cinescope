package pt.isel.ps.cinescope.repositories

import pt.isel.ps.cinescope.domain.Movie
import pt.isel.ps.cinescope.domain.MovieState

interface MoviesRepository {

    fun getMoviesListById(id: Int?, userId: Int?): List<Movie>

    fun addMovieToList(id: Int?,userId: Int?, movie: Movie)

    fun deleteMoviesList(id: Int?, userId: Int?)

    fun addMovieToUserData(id: Int?, userId: Int?, movie: Movie, state: MovieState)

    fun addMovieToMovieData(movie: Movie)

    fun getMovieFromMovieData(movieId: String?): Movie?

    fun getMovieFromMovieUserData(movieId: String?, userId: Int?): Movie?

}