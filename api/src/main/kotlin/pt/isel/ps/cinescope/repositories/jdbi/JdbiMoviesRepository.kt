package pt.isel.ps.cinescope.repositories.jdbi

import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.domain.ListDetails
import pt.isel.ps.cinescope.domain.Movie
import pt.isel.ps.cinescope.domain.MovieState
import pt.isel.ps.cinescope.repositories.MoviesRepository

class JdbiMoviesRepository(private val handle: Handle): MoviesRepository {

    override fun createList(userId: Int?, name: String): Int? {
        return handle.createUpdate("insert into cinescope.movieslists(mlid, userId, name) values(DEFAULT, :userId, :name )")
            .bind("userId", userId)
            .bind("name", name)
            .executeAndReturnGeneratedKeys()
            .mapTo(Int::class.java)
            .firstOrNull()
    }

    override fun getMoviesListById(id: Int?, userId: Int?): List<Movie> {
        return handle.createQuery("select md.mimdbid as imdbid, md.mtmdbId as tmdbId, md.name, md.image as img, state " +
                                    "from cinescope.movieslists mls inner join cinescope.movielist ml on ml.mlid = mls.mlid " +
                                    "inner join cinescope.moviesdata md on md.mimdbid = ml.mimdbid " +
                                    "inner join cinescope.movieuserdata mud on mud.mimdbid = md.mimdbid " +
                                    "where mls.mlid = :id and mls.userid = :userId")
            .bind("id",id)
            .bind("userId", userId)
            .mapTo(Movie::class.java)
            .list()
    }

    override fun addMovieToList(id: Int?, userId: Int?, movie: Movie) {
        handle.createUpdate("insert into cinescope.movielist(mimdbid, mlid) values(:mimdbid, :mlid)")
            .bind("mimdbid", movie.imdbId)
            .bind("mlid", id)
            .execute()
    }

    override fun deleteMoviesList(id: Int?, userId: Int?) {
        handle.createUpdate("delete from cinescope.movieslists where mlid = :id and movieslists.userId = :userId ")
            .bind("id", id)
            .bind("userId", userId)
            .execute()
    }

    override fun addMovieToUserData(userId: Int?, movie: Movie, state: MovieState) {
        handle.createUpdate("insert into cinescope.movieuserdata(mimdbid, userId, state) values(:mimdbid, :userId, :state)")
            .bind("mimdbid", movie.imdbId)
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

    override fun getMovieFromMovieData(movieId: String?): Movie? {
        return handle.createQuery("select mimdbid as imdbId, mtmdbid as tmdbId, name, image as img from cinescope.moviesdata where mimdbid = :movieId")
            .bind("movieId", movieId)
            .mapTo(Movie::class.java)
            .firstOrNull()
    }

    override fun getMovieFromMovieUserData(movieId: String?, userId: Int?): Movie? {
        return handle.createQuery("select * from cinescope.movieuserdata where mimdbid = :movieId")
            .bind("movieId", movieId)
            .mapTo(Movie::class.java)
            .firstOrNull()
    }

    override fun changeState(movieId: String?, userId: Int?, state: MovieState) {
        handle.createUpdate("update cinescope.movieuserdata set state = :state where userId = :userId and mimdbid = :movieId")
            .bind("state", state)
            .bind("userId", userId)
            .bind("movieId", movieId)
            .execute()
    }

    override fun deleteMovieFromList(listId: Int?, movieId: String?, userId: Int?) {
        handle.createUpdate("delete from cinescope.movieList ml using cinescope.moviesLists mls " +
                                "where mls.userId = :userId and ml.mlid = :listId and ml.mlid = mls.mlid and ml.mimdbid = :movieId")
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
                "from cinescope.movieuserdata mud inner join cinescope.moviesdata md on md.mimdbid = mud.mimdbid " +
                "where mud.userid = :userId and mud.state = :state")
            .bind("userId", userId)
            .bind("state",state)
            .mapTo(Movie::class.java)
            .list()
    }

}