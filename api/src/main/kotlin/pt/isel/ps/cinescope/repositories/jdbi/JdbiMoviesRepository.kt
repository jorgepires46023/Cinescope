package pt.isel.ps.cinescope.repositories.jdbi

import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.domain.ListDetails
import pt.isel.ps.cinescope.domain.Movie
import pt.isel.ps.cinescope.domain.MovieState
import pt.isel.ps.cinescope.repositories.MoviesRepository

class JdbiMoviesRepository(private val handle: Handle): MoviesRepository {

    override fun createList(userId: Int?, name: String): Int? {
        return handle.createUpdate("insert into cinescope.movieslists(mlid, userId, name) values(DEFAULT, :userId, :name)")
            .bind("userId", userId)
            .bind("name", name)
            .executeAndReturnGeneratedKeys()
            .mapTo(Int::class.java)
            .firstOrNull()
    }

    override fun getMoviesListById(id: Int?, userId: Int?): List<Movie> {
        return handle.createQuery("select md.mimdbid as imdbid, md.mtmdbId as tmdbId, md.name, md.image as img, state " +
                                    "from cinescope.movieslists mls inner join cinescope.movielist ml on ml.mlid = mls.mlid " +
                                    "inner join cinescope.moviesdata md on md.mtmdbid = ml.mtmdbid " +
                                    "inner join cinescope.movieuserdata mud on mud.mtmdbid = md.mtmdbid " +
                                    "where mls.mlid = :id and mls.userid = :userId")
            .bind("id",id)
            .bind("userId", userId)
            .mapTo(Movie::class.java)
            .list()
    }

    override fun addMovieToList(listId: Int?, movie: Movie) {
        handle.createUpdate("insert into cinescope.movielist(mtmdbid, mlid) values(:mtmdbid, :mlid)")
            .bind("mtmdbid", movie.tmdbId)
            .bind("mlid", listId)
            .execute()
    }

    override fun deleteMoviesList(listId: Int?, userId: Int?) {
        handle.createUpdate("delete from cinescope.movieslists where mlid = :listId and userId = :userId ")
            .bind("listId", listId)
            .bind("userId", userId)
            .execute()
    }

    override fun addMovieToUserData(userId: Int?, movieId: Int, state: MovieState) {
        handle.createUpdate("insert into cinescope.movieuserdata(mtmdbid, userId, state) values(:mtmdbid, :userId, :state)")
            .bind("mtmdbid", movieId)
            .bind("userId",userId)
            .bind("state", state)
            .execute()
    }

    override fun addMovieToMovieData(movie: Movie) {
        handle.createUpdate("insert into cinescope.moviesdata(mimdbid, mtmdbid, name, image) values(:mimdbid, :mtmdbid, :name, :image)")
            .bind("mimdbid", movie.imdbId)
            .bind("mtmdbid", movie.tmdbId)
            .bind("name", movie.name)
            .bind("image", movie.img)
            .execute()
    }

    override fun getMovieFromMovieData(movieId: Int?): Movie? {
        return handle.createQuery("select mimdbid as imdbId, mtmdbid as tmdbId, name, image as img from cinescope.moviesdata where mtmdbid = :movieId")
            .bind("movieId", movieId)
            .mapTo(Movie::class.java)
            .firstOrNull()
    }

    override fun getMovieFromMovieUserData(movieId: Int?, userId: Int?): Movie? {
        return handle.createQuery("select * from cinescope.movieuserdata where mtmdbid = :movieId and userid = :userId")
            .bind("movieId", movieId)
            .bind("userId", userId)
            .mapTo(Movie::class.java)
            .firstOrNull()
    }

    override fun changeState(movieId: Int?, userId: Int?, state: MovieState) {
        handle.createUpdate("update cinescope.movieuserdata set state = :state where userId = :userId and mtmdbid = :movieId")
            .bind("state", state)
            .bind("userId", userId)
            .bind("movieId", movieId)
            .execute()
    }

    override fun deleteMovieFromList(listId: Int?, movieId: Int?, userId: Int?) {
        handle.createUpdate("delete from cinescope.movieList ml using cinescope.moviesLists mls " +
                                "where mls.userId = :userId and ml.mlid = :listId and ml.mlid = mls.mlid and ml.mtmdbid = :movieId")
            .bind("userId", userId)
            .bind("listId", listId)
            .bind("movieId", movieId)
            .execute()
    }

    override fun getLists(userId: Int?): List<ListDetails> {
        return handle.createQuery("select mlid as id, name from cinescope.moviesLists where userid = :userId")
            .bind("userId",userId)
            .mapTo(ListDetails::class.java)
            .list()
    }

    override fun getMoviesFromUserByState(userId: Int?, state: MovieState?): List<Movie> {
        return handle.createQuery("select md.mimdbid as imdbid, md.mtmdbId as tmdbId, md.name, md.image as img, mud.state " +
                "from cinescope.movieuserdata mud inner join cinescope.moviesdata md on md.mtmdbid = mud.mtmdbid " +
                "where mud.userid = :userId and mud.state = :state")
            .bind("userId", userId)
            .bind("state",state)
            .mapTo(Movie::class.java)
            .list()
    }

    override fun deleteMovieFromUserData(movieId: Int?, userId: Int?) {
        handle.createUpdate("delete from cinescope.movieuserdata where mtmdbid = :movieId and userid = :userId")
            .bind("movieId", movieId)
            .bind("userId",userId)
            .execute()
    }

    override fun removeStateFromMovies(movieId: Int, userId: Int?) {
        handle.createUpdate("call cinescope.removestatefrommovie(:movieId, :userId)")
            .bind("movieId", movieId)
            .bind("userId", userId)
            .execute()
    }

    override fun deleteMoviesFromList(listId: Int?) {
        handle.createUpdate("delete from cinescope.movielist where mlid = :mlid")
            .bind("mlid", listId)
            .execute()
    }
}