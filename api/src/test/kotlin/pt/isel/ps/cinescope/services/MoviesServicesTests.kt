package pt.isel.ps.cinescope.services

import org.junit.jupiter.api.Test
import pt.isel.ps.cinescope.domain.MovieState
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.testWithTransactionManagerAndRollback
import pt.isel.ps.cinescope.utils.SHA256Encoder
import pt.isel.ps.cinescope.utils.TmdbService
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MoviesServicesTests {

    //var id: Int? = 0

    companion object {
        val encoder = SHA256Encoder()
        val tmdbService = TmdbService()
    }

    /*@BeforeEach
    fun createUser() {

            val usersServices = UsersServices(encoder, transactionManager)

            id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

    }
    */

    @Test
    fun `create Movies List`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            moviesServices.createList(userId, "Action Movies")

            val moviesList = moviesServices.getLists(userId)

            assertEquals(moviesList[0].name, "Action Movies")

        }
    }

    @Test
    fun `create Movies List without a name`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "No user ID or Name for the List provided") {
                moviesServices.createList(id, null)
            }

            assertFailsWith<BadRequestException>(message = "No user ID or Name for the List provided") {
                moviesServices.createList(id, "")
            }

        }
    }


    @Test
    fun `create Movies without the Users Id` () {
        testWithTransactionManagerAndRollback { transactionManager ->

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "No user ID or Name for the List provided") {
                moviesServices.createList(null, "Action Movies")
            }

            assertFailsWith<BadRequestException>(message = "No user ID or Name for the List provided") {
                moviesServices.createList(null, "Action Movies")
            }

        }
    }

    //TODO ver como fazer este test
    @Test
    fun `Get list for a User`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            val moviesList = moviesServices.getList(listId, id)

            assertNotNull(moviesList)
        }
    }

    @Test
    fun `Get list for a User without the users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "Missing information to get the list") {
                moviesServices.getList(1, null)
            }

        }
    }

    @Test
    fun `Get list for a User without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "Missing information to get the list") {
                moviesServices.getList(null, 2)
            }

        }
    }

    @Test
    fun `Delete a list`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            moviesServices.createList(id, "Action Movies")

            val moviesLists = moviesServices.getLists(id)

            assertEquals(moviesLists[0].name, "Action Movies")

            moviesServices.deleteList(moviesLists[0].id, id)

            val moviesList = moviesServices.getList(moviesLists[0].id, id)

            assertTrue(moviesList.isEmpty())

        }
    }

    @Test
    fun `Delete a list without the users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "No user Id or list Id provided") {
                moviesServices.deleteList(38, null)
            }

        }
    }

    @Test
    fun `Delete a list without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "No user Id or list Id provided") {
                moviesServices.deleteList(null, 88)
            }

        }
    }

    @Test
    fun `Get all Lists for a User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            moviesServices.createList(id, "Action Movies")

            moviesServices.createList(id, "Favourite Movies")

            moviesServices.createList(id, "Drama Movies")

            val moviesLists = moviesServices.getLists(id)

            assertEquals(moviesLists[0].name, "Action Movies")
            assertEquals(moviesLists[1].name, "Favourite Movies")
            assertEquals(moviesLists[2].name, "Drama Movies")

        }
    }

    @Test
    fun `Get all Lists for a User without the User's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "No user ID provided") {
                moviesServices.getLists(null)
            }
        }
    }


    @Test
    fun`add a movie to a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            moviesServices.addMovieToList(245891,"tt2911666",listId,id)

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData("tt2911666") }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData("tt2911666", id) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, id)

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


        }

    }


    @Test
    fun`add a movie to a list without the movies imdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            assertFailsWith<BadRequestException>(message = "Missing information to add movie to list") {
                moviesServices.addMovieToList(245891,null,listId,id)
            }
            assertFailsWith<BadRequestException>(message = "Missing information to add movie to list") {
                moviesServices.addMovieToList(245891,"",listId,id)
            }
        }

    }

    @Test
    fun`add a movie to a list without the movies tmdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            assertFailsWith<BadRequestException>(message = "Missing information to add movie to list") {
                moviesServices.addMovieToList(null,"tt2911666",listId,id)
            }

        }

    }

    @Test
    fun`add a movie to a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "Missing information to add movie to list") {
                moviesServices.addMovieToList(245891,"tt2911666",null,id)
            }

        }
    }

    @Test
    fun`add a movie to a list without the users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            assertFailsWith<BadRequestException>(message = "Missing information to add movie to list") {
                moviesServices.addMovieToList(245891,"tt2911666",listId,null)
            }

        }

    }

    //TODO ver o que está a ser lançado como erro pela tmdb API
    @Test
    fun`add a movie to a list but the movie doesn't exists`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            assertFailsWith<NotFoundException> {
                moviesServices.addMovieToList(316788486,"tt2911666",listId,id)
            }

        }

    }

    @Test
    fun`delete a movie from a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            moviesServices.addMovieToList(245891,"tt2911666",listId,id)

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData("tt2911666") }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData("tt2911666", id) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, id)

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            moviesServices.deleteMovieFromList(listId,"tt2911666", id)

            val list = moviesServices.getList(listId, id)
            assertTrue(list.isEmpty())

        }

    }

    @Test
    fun `delete a movie from a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException>(message = "Missing information to delete movie from this list") {
                moviesServices.deleteMovieFromList(null,"tt2911666", id)
            }

        }

    }

    @Test
    fun`delete a movie from a list without users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            assertFailsWith<BadRequestException>(message = "Missing information to delete movie from this list") {
                moviesServices.deleteMovieFromList(listId,"tt2911666", null)
            }

        }

    }

    @Test
    fun`delete a movie from a list without the movie's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            assertFailsWith<BadRequestException>(message = "Missing information to delete movie from this list") {
                moviesServices.deleteMovieFromList(listId,null, id)
            }

            assertFailsWith<BadRequestException>(message = "Missing information to delete movie from this list") {
                moviesServices.deleteMovieFromList(listId,"*", id)
            }

        }

    }

    //TODO ver o que é lançado quando o movie não existe na lista
    @Test
    fun `delete a movie from a list but the movie is not on the list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            assertFailsWith<NotFoundException> {
                moviesServices.deleteMovieFromList(listId, "bnakjbgi", id)
            }

        }

    }

    @Test
    fun`change the state of a movie in a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            moviesServices.addMovieToList(245891,"tt2911666",listId,id)

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData("tt2911666") }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData("tt2911666", id) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, id)

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)

            moviesServices.changeState("tt2911666","Watched",id)

            val moviesUserDataChanged = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData("tt2911666", id) }

            //TODO mudar Watched para WATCHED(DB) ou vice versa
            assertEquals(moviesUserDataChanged?.state, MovieState.PTW)


        }

    }

    @Test
    fun`change the state of a movie in a list without providing the state to change to`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            moviesServices.addMovieToList(245891,"tt2911666",listId,id)

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData("tt2911666") }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData("tt2911666", id) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, id)

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException>(message = "State not valid") {
                moviesServices.changeState("tt2911666","Banana",id)
            }

            assertFailsWith<BadRequestException>(message = "State not valid") {
                moviesServices.changeState("tt2911666","",id)
            }

            assertFailsWith<BadRequestException>(message = "State not valid") {
                moviesServices.changeState("tt2911666",null,id)
            }


        }

    }

    @Test
    fun`change the state of a movie in a list without the movie's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            moviesServices.addMovieToList(245891,"tt2911666",listId,id)

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData("tt2911666") }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData("tt2911666", id) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, id)

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException>(message = "Missing information to change this state") {
                moviesServices.changeState(null,"Watched",id)
            }

            assertFailsWith<BadRequestException>(message = "Missing information to change this state") {
                moviesServices.changeState("","Watched",id)
            }

        }

    }

    @Test
    fun`change the state of a movie in a list without the users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val moviesServices = MoviesServices(transactionManager, tmdbService)

            val listId = moviesServices.createList(id, "Action Movies")

            moviesServices.addMovieToList(245891,"tt2911666",listId,id)

            val movieData = transactionManager.run { it.moviesRepository.getMovieFromMovieData("tt2911666") }

            assertEquals(movieData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")

            val moviesUserData = transactionManager.run { it.moviesRepository.getMovieFromMovieUserData("tt2911666", id) }

            //TODO ver ser è melhor criar outra class para movies User data
            /*assertEquals(moviesUserData?.name, "John Wick")
            assertEquals(movieData?.imdbId, "tt2911666")
            assertEquals(movieData?.tmdbId, 245891)
            assertEquals(movieData?.img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")*/
            assertEquals(moviesUserData?.state, MovieState.PTW)

            val movieList = moviesServices.getList(listId, id)

            assertEquals(movieList[0].name, "John Wick")
            assertEquals(movieList[0].imdbId, "tt2911666")
            assertEquals(movieList[0].tmdbId, 245891)
            assertEquals(movieList[0].img, "/wXqWR7dHncNRbxoEGybEy7QTe9h.jpg")
            assertEquals(movieList[0].state, MovieState.PTW)


            assertFailsWith<BadRequestException>(message = "Missing information to change this state") {
                moviesServices.changeState("tt2911666","Banana", null)
            }


        }

    }

}