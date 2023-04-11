package pt.isel.ps.cinescope.repositories.jdbi

import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.domain.Movie
import pt.isel.ps.cinescope.domain.MovieState
import pt.isel.ps.cinescope.repositories.MoviesRepository

class JdbiMoviesRepository(private val handle: Handle): MoviesRepository {

    override fun createList(userId: Int?, name: String) {
        handle.createUpdate("insert into movieslists(mlid, userId, name) values(DEFAULT, :userId, :name )")
            .bind("userId", userId)
            .bind("name", name)
            .execute()
    }

    override fun getMoviesListById(id: Int?, userId: Int?): List<Movie> {
        return handle.createQuery("select * from moviesLists inner join movieList on movieList.mlid = moviesLists.mlid" +
                                    "inner join moviesdata on moviesdata.mimdbid = movieList.mimdbid" +
                                    "inner join movieuserdata on movieuserdata.mimdbid = moviesdata." +
                                    "where movieslists.mlid = :id and movieslists.userid = :userId")
            .bind("id",id)
            .bind("userId", userId)
            .mapTo(Movie::class.java)
            .list()
    }

    override fun addMovieToList(id: Int?, userId: Int?, movie: Movie) {
        handle.createUpdate("insert into movelist(mimdbid, mlid) values(:mimdbid, :mlid)")
            .bind("mimdbid", movie.imdbId)
            .bind("mlid", id)
            .execute()
    }

    override fun deleteMoviesList(id: Int?, userId: Int?) {
        handle.createUpdate("delete from movieslists where mlid = :id and movieslists.userId = :userId ")
            .bind("id", id)
            .bind("userId", userId)
            .execute()
    }

    override fun addMovieToUserData(id: Int?, userId: Int?, movie: Movie, state: MovieState) {
        handle.createUpdate("insert into movieuserdata(mimdcid, userId, state) values(:mimdbid, :userId, :state)")
            .bind("mimdbid", movie.imdbId)
            .bind("userId",userId)
            .bind("state", state)
            .execute()
    }

    override fun addMovieToMovieData(movie: Movie) {
        handle.createUpdate("insert into moviesdata(mimdbid, mtmdbid, name, image) values(:mimdbid, :mtmdbid, :name, :image)")
            .bind("mimdbid", movie.imdbId)
            .bind("mtmdbid", movie.tmdbId)
            .bind("name", movie.name)
            .bind("image", movie.img)
            .execute()
    }

    override fun getMovieFromMovieData(movieId: String?): Movie? {
        return handle.createQuery("select * from moviesdata where mimdbid = :movieId")
            .bind("movieId", movieId)
            .mapTo(Movie::class.java)
            .firstOrNull()
    }

    override fun getMovieFromMovieUserData(movieId: String?, userId: Int?): Movie? {
        return handle.createQuery("select * from movieuserdata where mimdbid = :movieId")
            .bind("movieId", movieId)
            .mapTo(Movie::class.java)
            .firstOrNull()
    }


}