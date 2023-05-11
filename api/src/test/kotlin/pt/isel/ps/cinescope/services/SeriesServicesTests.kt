package pt.isel.ps.cinescope.services

import org.junit.jupiter.api.Test
import pt.isel.ps.cinescope.domain.SeriesState
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
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

        }
    }

    @Test
    fun `Add a Series to a list without the series imdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList(null,66732,listId,userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList("",66732,listId,userId)
            }

        }
    }

    @Test
    fun `Add a Series to a list without the series tmdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList("tt4574334",null,listId,userId)
            }

        }
    }

    @Test
    fun `Add a Series to a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList("tt4574334",66732,null,userId)
            }

        }
    }

    @Test
    fun `Add a Series to a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList("tt4574334",66732,listId,null)
            }

        }
    }

    //TODO ver como vem da API externa
    @Test
    fun `Add a Series to a list but the Series doesn't exists`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList("tt4574334",66732,listId,userId)
            }

        }
    }

    @Test
    fun `Delete a series from a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.deleteSeriesFromList(listId, "tt4574334", userId)

            val list = seriesServices.getList(listId, userId)

            assertTrue(list.isEmpty())
        }
    }

    @Test
    fun `Delete a series from a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            //TODO apagar?
            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)
            //TODO

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(null, "tt4574334", userId)
            }
        }
    }

    @Test
    fun `Delete a series from a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            //TODO apagar?
            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)
            //TODO

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, "tt4574334", null)
            }
        }
    }

    @Test
    fun `Delete a series from a list without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            //TODO apagar?
            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)
            //TODO

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, "", userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, null, userId)
            }
        }
    }

    //TODO ver como vem da DB
    @Test
    fun `Delete a series from a list but the series is not on the list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            //TODO apagar?
            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)
            //TODO

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, "tt4574334", userId)
            }

        }
    }

    @Test
    fun `Change the state of a series in a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.changeState("tt4574334", "Watching", userId)

            val seriesState1 = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            assertEquals(seriesState1?.state, SeriesState.Watching)

            seriesServices.changeState("tt4574334", "Watched", userId)

            val seriesState2 = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            assertEquals(seriesState2?.state, SeriesState.Watched)

        }
    }

    @Test
    fun `Change the state of a series in a list without providing the state to change to`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.changeState("tt4574334", null, userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.changeState("tt4574334", "", userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.changeState("tt4574334", "Banana", userId)
            }

        }
    }

    @Test
    fun `Change the state of a series in a list without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.changeState("", "Watching", userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.changeState(null, "Watching", userId)
            }

        }
    }

    @Test
    fun `Change the state of a series in a list without the users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.changeState("tt4574334", "Watching", null)
            }

        }
    }

    @Test
    fun `Get the Watched Episodes list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            val wepList = seriesServices.getWatchedEpList("tt4574334",userId)

            assertTrue(wepList.isEmpty())
        }
    }


    @Test
    fun `Get the Watched Episodes list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334", 66732, listId, userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData =
                transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.getWatchedEpList("tt4574334", null)
            }

        }
    }

    @Test
    fun `Get the Watched Episodes list without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334", 66732, listId, userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData =
                transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.getWatchedEpList("", userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.getWatchedEpList(null, userId)
            }


        }

    }

    @Test
    fun `Add a watched episode to the Watched Episodes List`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,userId)

            val wepList = seriesServices.getWatchedEpList("tt4574334", userId)

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].imdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

        }
    }


    @Test
    fun `Add a watched episode to the Watched Episodes List without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,null)
            }

        }
    }

    @Test
    fun `Add a watched episode to the Watched Episodes List without the series tmdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(null,"tt4593118",1,1,userId)
            }

        }

    }

    @Test
    fun `Add a watched episode to the Watched Episodes List without the episode imdbId, episode number and season number`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,null,1,1,userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"",1,1,userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",null,1,userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,null,userId)
            }

        }

    }

    @Test
    fun `Remove a Episode from the Watched Episodes List`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,userId)

            val wepList = seriesServices.getWatchedEpList("tt4574334", userId)

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].imdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            seriesServices.removeWatchedEpisode("tt4574334","tt4593118",userId)

            val wepList1 = seriesServices.getWatchedEpList("tt4574334", userId)

            assertTrue(wepList1.isEmpty())
        }
    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,userId)

            val wepList = seriesServices.getWatchedEpList("tt4574334", userId)

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].imdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode("tt4574334","tt4593118",null)
            }

        }
    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334",66732,listId,userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,userId)

            val wepList = seriesServices.getWatchedEpList("tt4574334", userId)

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].imdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode("","tt4593118",userId)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode(null,"tt4593118",userId)
            }

        }
    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the episode imdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334", 66732, listId, userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData =
                transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId, "tt4593118", 1, 1, userId)

            val wepList = seriesServices.getWatchedEpList("tt4574334", userId)

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name, "Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].imdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode("tt4574334", "", userId)
            }
            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode("tt4574334", null, userId)
            }
        }
    }

    //TODO ver como ver da DB
    @Test
    fun `Remove a Episode from the Watched Episodes List but the episode is not in the list`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            seriesServices.addSeriesToList("tt4574334", 66732, listId, userId)

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData("tt4574334") }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData =
                transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData("tt4574334", userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, userId)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId, "tt4593118", 1, 1, userId)

            val wepList = seriesServices.getWatchedEpList("tt4574334", userId)

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name, "Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].imdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            seriesServices.removeWatchedEpisode("tt4574334", "tt4593118", userId)
        }
    }

    @Test
    fun `Get List of Series with the same State`() {
        testWithTransactionManagerAndRollback { transactionManager ->
            val usersServices = UsersServices(encoder, transactionManager)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val seriesServices = SeriesServices(transactionManager, tmdbService)

            val listId = seriesServices.createList(userId, "Fantasy Series")

            val listId1 = seriesServices.createList(userId, "Favourite Series")

            val listId2 = seriesServices.createList(userId, "Action Series")

            seriesServices.addSeriesToList("tt4574334", 66732, listId, userId)

            seriesServices.addSeriesToList("tt1520211", 1402, listId, userId)

            seriesServices.addSeriesToList("tt2805096", 58841, listId, userId)

            seriesServices.changeState("tt1520211","Watched", userId)

            seriesServices.changeState("tt4574334","Watching", userId)

            val seriesList = seriesServices.getSeriesFromUserByState(userId, "Watching")

            assertEquals(seriesList.size, 1)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.Watching)

        }
    }

}