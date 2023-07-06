package pt.isel.ps.cinescope.services

import org.junit.jupiter.api.Test
import pt.isel.ps.cinescope.controllers.TokenProcessor
import pt.isel.ps.cinescope.domain.MovieState
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.testWithTransactionManagerAndRollback
import pt.isel.ps.cinescope.utils.SHA256Encoder
import pt.isel.ps.cinescope.utils.TmdbRepository
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MoviesServicesTests {

    companion object {
        val encoder = SHA256Encoder()
        val tmdbRepository = TmdbRepository()
        val searchServices = SearchServices(tmdbRepository)
    }

    @Test
    fun `create Movies List`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val moviesList = moviesServices.getLists("bearer ${user?.token.toString()}")

            assertEquals(moviesList[0].name, "Action Movies")
        }
    }

    @Test
    fun `create Movies List without a name`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.createList("bearer ${user?.token.toString()}", null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.createList("bearer ${user?.token.toString()}", "")
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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val moviesList = moviesServices.getList(listId, "bearer ${user?.token.toString()}")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.getList(null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Delete a list`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val moviesLists = moviesServices.getLists("bearer ${user?.token.toString()}")

            assertEquals(moviesLists[0].name, "Action Movies")

            moviesServices.deleteList(moviesLists[0].id,"bearer ${user?.token.toString()}")

            val moviesList = moviesServices.getList(moviesLists[0].id, "bearer ${user?.token.toString()}")

            assertTrue(moviesList.isEmpty())
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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.deleteList(null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Delete a list with Movies in it`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val moviesLists = moviesServices.getLists("bearer ${user?.token.toString()}")

            assertEquals(moviesLists[0].name, "Action Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

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

            moviesServices.deleteList(moviesLists[0].id, "bearer ${user?.token.toString()}")

            val moviesList = moviesServices.getList(moviesLists[0].id, "bearer ${user?.token.toString()}")

            assertTrue(moviesList.isEmpty())
        }
    }

    @Test
    fun `Get all Lists for a User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            moviesServices.createList("bearer ${user?.token.toString()}", "Favourite Movies")

            moviesServices.createList("bearer ${user?.token.toString()}", "Drama Movies")

            val moviesLists = moviesServices.getLists("bearer ${user?.token.toString()}")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

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

            val movieList = moviesServices.getList(listId, "bearer ${user?.token.toString()}")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(null,listId,"bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun`add a movie to a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(245891,null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun`add a movie to a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(245891,listId, null)
            }

            assertFailsWith<BadRequestException> {
                moviesServices.addMovieToList(245891,listId, "")
            }
        }
    }

    //TODO ver o que está a ser lançado como erro pela tmdb API
    @Test
    fun`add a movie to a list but the movie doesn't exists`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            assertFailsWith<NotFoundException> {
                moviesServices.addMovieToList(316788486,listId,"bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun`delete a movie from a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

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

            val movieList = moviesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            moviesServices.deleteMovieFromList(listId,245891, "bearer ${user?.token.toString()}")

            val list = moviesServices.getList(listId, "bearer ${user?.token.toString()}")
            assertTrue(list.isEmpty())
        }
    }

    @Test
    fun `delete a movie from a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                moviesServices.deleteMovieFromList(null, 245891, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun`delete a movie from a list without Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            assertFailsWith<BadRequestException> {
                moviesServices.deleteMovieFromList(listId,null, "bearer ${user?.token.toString()}")
            }
        }
    }

    //TODO ver o que é lançado quando o movie não existe na lista
    @Test
    fun `delete a movie from a list but the movie is not on the list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            assertFailsWith<NotFoundException> {
                moviesServices.deleteMovieFromList(listId, 568591698, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun`change the state of a movie in a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

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

            val movieList = moviesServices.getList(listId,"bearer ${user?.token.toString()}")

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)

            moviesServices.changeState(245891,"Watched", "bearer ${user?.token.toString()}")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            moviesServices.changeState(245891,"Watched", "bearer ${user?.token.toString()}")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            moviesServices.addMovieToList(245891,listId, "bearer ${user?.token.toString()}")

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

            val movieList = moviesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,"Banana","bearer ${user?.token.toString()}")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,"", "bearer ${user?.token.toString()}")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.changeState(245891,null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun`change the state of a movie in a list without the movie's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            moviesServices.addMovieToList(245891,listId, "bearer ${user?.token.toString()}")

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

            val movieList = moviesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException> {
                moviesServices.changeState(null,"Watched", "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun`change the state of a movie in a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            moviesServices.addMovieToList(245891,listId, "bearer ${user?.token.toString()}")

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

            val movieList = moviesServices.getList(listId, "bearer ${user?.token.toString()}")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val listId1 = moviesServices.createList("bearer ${user?.token.toString()}", "Favourite Movies")

            val listId2 = moviesServices.createList("bearer ${user?.token.toString()}", "Drama Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

            moviesServices.addMovieToList(11324,listId1,"bearer ${user?.token.toString()}")

            moviesServices.addMovieToList(964980,listId2,"bearer ${user?.token.toString()}")

            moviesServices.changeState(11324,"Watched", "bearer ${user?.token.toString()}")

            val moviesList = moviesServices.getMoviesFromUserByState("bearer ${user?.token.toString()}", "PTW")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val listId1 = moviesServices.createList("bearer ${user?.token.toString()}", "Drama Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

            moviesServices.addMovieToList(964980,listId1,"bearer ${user?.token.toString()}")

            moviesServices.changeState(245891,"Watched", "bearer ${user?.token.toString()}")

            moviesServices.changeState(11324, "Watched", "bearer ${user?.token.toString()}")

            val moviesList = moviesServices.getMoviesFromUserByState("bearer ${user?.token.toString()}", "Watched")

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

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val listId1 = moviesServices.createList("bearer ${user?.token.toString()}", "Drama Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

            moviesServices.addMovieToList(964980,listId1,"bearer ${user?.token.toString()}")

            moviesServices.changeState(245891,"Watched", "bearer ${user?.token.toString()}")

            moviesServices.changeState(11324, "Watched", "bearer ${user?.token.toString()}")

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState("bearer ${user?.token.toString()}", "Banana")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState("bearer ${user?.token.toString()}", "")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState("bearer ${user?.token.toString()}", null)
            }
        }
    }

    @Test
    fun `Get List of Movies with the same state without the Users ID`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val moviesServices = MoviesServices(transactionManager, tmdbRepository, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = moviesServices.createList("bearer ${user?.token.toString()}", "Action Movies")

            val listId1 = moviesServices.createList("bearer ${user?.token.toString()}", "Drama Movies")

            moviesServices.addMovieToList(245891,listId,"bearer ${user?.token.toString()}")

            moviesServices.addMovieToList(964980,listId1,"bearer ${user?.token.toString()}")

            moviesServices.changeState(245891,"Watched", "bearer ${user?.token.toString()}")

            moviesServices.changeState(11324, "Watched", "bearer ${user?.token.toString()}")

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState(null, "Watched")
            }

            assertFailsWith<BadRequestException> {
                moviesServices.getMoviesFromUserByState("", "Watched")
            }
        }
    }



}