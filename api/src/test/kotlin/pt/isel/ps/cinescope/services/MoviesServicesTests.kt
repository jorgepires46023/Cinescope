package pt.isel.ps.cinescope.services

import org.junit.jupiter.api.Test
import pt.isel.ps.cinescope.controllers.TokenProcessor
import pt.isel.ps.cinescope.domain.Movie
import pt.isel.ps.cinescope.domain.MovieState
import pt.isel.ps.cinescope.repositories.tmdb.TmdbRepository
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.testWithTransactionManagerAndRollback
import pt.isel.ps.cinescope.utils.SHA256Encoder
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MoviesServicesTests {

    companion object {
        val encoder = SHA256Encoder()
        val tmdbRepository = TmdbRepository()
    }

    @Test
    fun `create Movies List`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            moviesServices.createList(user.token.toString(), "Action Movies")

            val moviesList = moviesServices.getLists(user.token.toString())

            assertEquals(moviesList[0].name, "Action Movies")
        }
    }

    @Test
    fun `create Movies List without a name`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.createList(user.token.toString(), null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.createList(user.token.toString(), "")
            }
        }
    }


    @Test
    fun `create Movies without the Users Id` () {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            assertFailsWith<BadRequestException> {
                moviesServices.createList(null, "Action Movies")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.createList("", "Action Movies")
            }
        }
    }

    @Test
    fun `Get list for a User`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            val moviesList = moviesServices.getList(listId, user.token.toString())

            assertNotNull(moviesList)
        }
    }

    @Test
    fun `Get list for a User without the users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            assertFailsWith<BadRequestException> {
                moviesServices.getList(1, null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getList(1, "")
            }
        }
    }

    @Test
    fun `Get list for a User without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.getList(null, user.token.toString())
            }
        }
    }

    @Test
    fun `Delete a list`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            moviesServices.createList(user.token.toString(), "Action Movies")

            val moviesLists = moviesServices.getLists(user.token.toString())

            assertEquals(moviesLists[0].name, "Action Movies")

            moviesServices.deleteList(moviesLists[0].id,user.token.toString())

            assertFailsWith<BadRequestException> {
                moviesServices.getList(moviesLists[0].id, user.token.toString())
            }
        }
    }

    @Test
    fun `Delete a list without the users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            assertFailsWith<BadRequestException> {
                moviesServices.deleteList(38, null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.deleteList(38, "")
            }
        }
    }

    @Test
    fun `Delete a list without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.deleteList(null, user.token.toString())
            }
        }
    }

    @Test
    fun `Delete a list with Movies in it`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            val moviesLists = moviesServices.getLists(user.token.toString())

            assertEquals(moviesLists[0].name, "Action Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            moviesServices.deleteList(moviesLists[0].id, user.token.toString())

            assertFailsWith<BadRequestException> {
                moviesServices.getList(moviesLists[0].id, user.token.toString())
            }
        }
    }

    @Test
    fun `Get all Lists for a User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            moviesServices.createList(user.token.toString(), "Action Movies")

            moviesServices.createList(user.token.toString(), "Favourite Movies")

            moviesServices.createList(user.token.toString(), "Drama Movies")

            val moviesLists = moviesServices.getLists(user.token.toString())

            assertEquals(moviesLists[0].name, "Action Movies")
            assertEquals(moviesLists[1].name, "Favourite Movies")
            assertEquals(moviesLists[2].name, "Drama Movies")
        }
    }

    @Test
    fun `Get all Lists for a User without the User's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            assertFailsWith<BadRequestException> {
                moviesServices.getLists(null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getLists("")
            }
        }
    }


    @Test
    fun`add a movie to a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, user.token.toString()).results as List<Movie>
            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)
        }
    }

    @Test
    fun`add a movie to a list without the movies tmdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(null,listId,user.token.toString())
            }
        }
    }

    @Test
    fun`add a movie to a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(245891,null, user.token.toString())
            }
        }
    }

    @Test
    fun`add a movie to a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(245891,listId, null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(245891,listId, "")
            }
        }
    }

    //TODO should be InternalServerErrorException
    @Test
    fun`add a movie to a list but the movie doesn't exists`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            assertFailsWith<Exception> {
                moviesServices.addMovieToList(316788486,listId,user.token.toString())
            }
        }
    }

    @Test
    fun`delete a movie from a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, user.token.toString()).results as List<Movie>

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            moviesServices.deleteMovieFromList(listId,245891, user.token.toString())

            val list = moviesServices.getList(listId, user.token.toString())
            assertTrue(list.results.isEmpty())
        }
    }

    @Test
    fun `delete a movie from a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.deleteMovieFromList(null, 245891, user.token.toString())
            }
        }
    }

    @Test
    fun`delete a movie from a list without Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            assertFailsWith<BadRequestException> {
                moviesServices.deleteMovieFromList(listId,245891, null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.deleteMovieFromList(listId,245891, "")
            }
        }
    }

    @Test
    fun`delete a movie from a list without the movie's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            assertFailsWith<BadRequestException> {
                moviesServices.deleteMovieFromList(listId,null, user.token.toString())
            }
        }
    }


    @Test
    fun`change the state of a movie in a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId,user.token.toString()).results as List<Movie>

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)

            moviesServices.changeState(245891,"Watched", user.token.toString())

            val moviesUserDataChanged = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            assertEquals(moviesUserDataChanged?.state, MovieState.Watched)
        }
    }

    @Test
    fun`change the state of a movie that is not in a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            moviesServices.changeState(245891,"Watched", user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.Watched)
        }
    }


    @Test
    fun`change the state of a movie in a list without providing the state to change to`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            moviesServices.addMovieToList(245891,listId, user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, user.token.toString()).results as List<Movie>

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,"Banana",user.token.toString())
            }

            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,"", user.token.toString())
            }

            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,null, user.token.toString())
            }
        }
    }

    @Test
    fun`change the state of a movie in a list without the movie's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            moviesServices.addMovieToList(245891,listId, user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, user.token.toString()).results as List<Movie>

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException> {
                moviesServices.changeState(null,"Watched", user.token.toString())
            }
        }
    }

    @Test
    fun`change the state of a movie in a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            moviesServices.addMovieToList(245891,listId, user.token.toString())

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData(245891) }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData(245891, userId) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, user.token.toString()).results as List<Movie>

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,"Watched", null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,"Watched", "")
            }
        }
    }

    @Test
    fun `Get List of Movies with the same state`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            val listId1 = moviesServices.createList(user.token.toString(), "Favourite Movies")

            val listId2 = moviesServices.createList(user.token.toString(), "Drama Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            moviesServices.addMovieToList(11324,listId1,user.token.toString())

            moviesServices.addMovieToList(964980,listId2,user.token.toString())

            moviesServices.changeState(11324,"Watched", user.token.toString())

            val moviesList = moviesServices.getMoviesFromUserByState(user.token.toString(), "PTW")

            assertEquals(moviesList.size, 2)

            assertEquals(moviesList[0].name, "John Wick")
            assertEquals(moviesList[0].imdbId, "tt2911666")
            assertEquals(moviesList[0].tmdbId, 245891)
            assertEquals(moviesList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(moviesList[0].state, MovieState.PTW)

            assertEquals(moviesList[1].name, "Air")
            assertEquals(moviesList[1].imdbId, "tt16419074")
            assertEquals(moviesList[1].tmdbId, 964980)
            assertEquals(moviesList[1].img, "/76AKQPdH3M8cvsFR9K8JsOzVlY5.jpg")
            assertEquals(moviesList[1].state, MovieState.PTW)
        }
    }

    @Test
    fun `Get List of Movies with the same state but not all movies are in a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            val listId1 = moviesServices.createList(user.token.toString(), "Drama Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            moviesServices.addMovieToList(964980,listId1,user.token.toString())

            moviesServices.changeState(245891,"Watched", user.token.toString())

            moviesServices.changeState(11324, "Watched", user.token.toString())

            val moviesList = moviesServices.getMoviesFromUserByState(user.token.toString(), "Watched")

            assertEquals(moviesList.size, 2)

            assertEquals(moviesList[0].name, "John Wick")
            assertEquals(moviesList[0].imdbId, "tt2911666")
            assertEquals(moviesList[0].tmdbId, 245891)
            assertEquals(moviesList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(moviesList[0].state, MovieState.Watched)

            assertEquals(moviesList[1].name, "Shutter Island")
            assertEquals(moviesList[1].imdbId, "tt1130884")
            assertEquals(moviesList[1].tmdbId, 11324)
            assertEquals(moviesList[1].img, "/4GDy0PHYX3VRXUtwK5ysFbg3kEx.jpg")
            assertEquals(moviesList[1].state, MovieState.Watched)
        }
    }

    @Test
    fun `Get List of Movies with the same state without providing a state`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            val listId1 = moviesServices.createList(user.token.toString(), "Drama Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            moviesServices.addMovieToList(964980,listId1,user.token.toString())

            moviesServices.changeState(245891,"Watched", user.token.toString())

            moviesServices.changeState(11324, "Watched", user.token.toString())

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState(user.token.toString(), "Banana")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState(user.token.toString(), "")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState(user.token.toString(), null)
            }
        }
    }

    @Test
    fun `Get List of Movies with the same state without the Users ID`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior").id

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList(user.token.toString(), "Action Movies")

            val listId1 = moviesServices.createList(user.token.toString(), "Drama Movies")

            moviesServices.addMovieToList(245891,listId,user.token.toString())

            moviesServices.addMovieToList(964980,listId1,user.token.toString())

            moviesServices.changeState(245891,"Watched", user.token.toString())

            moviesServices.changeState(11324, "Watched", user.token.toString())

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState(null, "Watched")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState("", "Watched")
            }
        }
    }



}