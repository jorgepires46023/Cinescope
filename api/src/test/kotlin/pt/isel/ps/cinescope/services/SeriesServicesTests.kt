package pt.isel.ps.cinescope.services

import org.junit.jupiter.api.Test
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.testWithTransactionManagerAndRollback
import pt.isel.ps.cinescope.utils.SHA256Encoder
import pt.isel.ps.cinescope.utils.TmdbService
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SeriesServicesTests {
    companion object {
        val encoder = SHA256Encoder()
        val tmdbService = TmdbService()    }

    @Test
    fun `Create Series List`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            seriesServices.createList(userId, "Fantasy Series")

            val seriesLists = seriesServices.getLists(userId)

            assertEquals(seriesLists[0].name, "Fantasy Series")
        }
    }

    @Test
    fun `Create Series List without a name`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.createList(userId, null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.createList(userId, "")
            }
        }
    }

    @Test
    fun `Create Series List without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.createList(null, "Fantasy Series")
            }
        }
    }

    @Test
    fun `Get list for a User`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            val seriesList = seriesServices.getList(listId, userId)

            assertNotNull(seriesList)
        }
    }
    @Test
    fun `Get list for a User without the users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.getList(1, null)
            }

        }
    }

    @Test
    fun `Get list for a User without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.getList(null, 2)
            }

        }
    }

    @Test
    fun `Delete a list`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            seriesServices.createList(userId, "Fantasy Series")

            val seriesLists = seriesServices.getLists(userId)

            assertEquals(seriesLists[0].name, "Fantasy Series")

            seriesServices.deleteList(seriesLists[0].id, userId)

            val seriesList = seriesServices.getList(seriesLists[0].id, userId)

            assertTrue(seriesList.isEmpty())

        }
    }

    @Test
    fun `Delete a list without the users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteList(38, null)
            }

        }
    }

    @Test
    fun `Delete a list without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteList(null, userId)
            }

        }
    }

    @Test
    fun `Get all Lists for a User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            seriesServices.createList(userId, "Fantasy Series")

            seriesServices.createList(userId, "Favourite Series")

            seriesServices.createList(userId, "Sitcoms")

            val seriesList = seriesServices.getLists(userId)

            assertEquals(seriesList[0].name, "Fantasy Series")
            assertEquals(seriesList[1].name, "Favourite Series")
            assertEquals(seriesList[2].name, "Sitcoms")

        }
    }

    @Test
    fun `Get all Lists for a User without the User's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.getLists(null)
            }
        }
    }


    @Test
    fun `Add a Series to a list`() {

    }

    @Test
    fun `Add a Series to a list without the series imdbId`() {

    }

    @Test
    fun `Add a Series to a list without the series tmdbId`() {

    }

    @Test
    fun `Add a Series to a list without the list's Id`() {

    }

    @Test
    fun `Add a Series to a list without the Users Id`() {

    }

    @Test
    fun `Add a Series to a list but the Series doesn't exists`() {

    }

    @Test
    fun `Delete a series from a list`() {

    }

    @Test
    fun `Delete a series from a list without the list's Id`() {

    }

    @Test
    fun `Delete a series from a list without the Users Id`() {

    }

    @Test
    fun `Delete a series from a list without the series Id`() {

    }

    @Test
    fun `Delete a series from a list but the series is not on the list`() {

    }

    @Test
    fun `Change the state of a series in a list`() {

    }

    @Test
    fun `Change the state of a series in a list without providing the state to change to`() {

    }

    @Test
    fun `Change the state of a series in a list without the series Id`() {

    }

    @Test
    fun `Change the state of a series in a list without the users Id`() {

    }


    @Test
    fun `Add a watched episode to the Watched Episodes List`() {

    }


    @Test
    fun `Add a watched episode to the Watched Episodes List without the Users Id`() {

    }

    @Test
    fun `Add a watched episode to the Watched Episodes List without the series tmdbId`() {

    }

    @Test
    fun `Add a watched episode to the Watched Episodes List without the episode imdbId, episode number and season number`() {

    }



    @Test
    fun `Remove a Episode from the Watched Episodes List`() {

    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the Users Id`() {

    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the series Id`() {

    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the episode imdbId`() {

    }

    @Test
    fun `Remove a Episode from the Watched Episodes List but the episode is not in the list`() {

    }

    @Test
    fun `Get the Watched Episodes list`() {

    }


    @Test
    fun `Get the Watched Episodes list without the Users Id`() {

    }

    @Test
    fun `Get the Watched Episodes list without the series Id`() {

    }
    
}