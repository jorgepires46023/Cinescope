package pt.isel.ps.cinescope.services

import org.junit.jupiter.api.Test
import pt.isel.ps.cinescope.controllers.TokenProcessor
import pt.isel.ps.cinescope.domain.SeriesState
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.testWithTransactionManagerAndRollback
import pt.isel.ps.cinescope.utils.SHA256Encoder
import pt.isel.ps.cinescope.utils.TmdbRepository
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SeriesServicesTests {
    companion object {
        val encoder = SHA256Encoder()
        val searchServices = SearchServices(TmdbRepository())
    }

    @Test
    fun `Create Series List`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            val seriesLists = seriesServices.getLists("bearer ${user?.token.toString()}")

            assertEquals(seriesLists[0].name, "Fantasy Series")
        }
    }

    @Test
    fun `Create Series List without a name`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                seriesServices.createList("bearer ${user?.token.toString()}", null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.createList("bearer ${user?.token.toString()}", "")
            }
        }
    }

    @Test
    fun `Create Series List without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            assertFailsWith<BadRequestException> {
                seriesServices.createList(null, "Fantasy Series")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.createList("", "Fantasy Series")
            }
        }
    }

    @Test
    fun `Get list for a User`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertNotNull(seriesList)
        }
    }

    @Test
    fun `Get list for a User without the Users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.getList(listId, null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.getList(listId, "")
            }
        }
    }

    @Test
    fun `Get list for a User without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                seriesServices.getList(null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Delete a list`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            val seriesLists = seriesServices.getLists("bearer ${user?.token.toString()}")

            assertEquals(seriesLists[0].name, "Fantasy Series")

            seriesServices.deleteList(seriesLists[0].id, "bearer ${user?.token.toString()}")

            val seriesList = seriesServices.getList(seriesLists[0].id, "bearer ${user?.token.toString()}")

            assertTrue(seriesList.isEmpty())
        }
    }

    @Test
    fun `Delete a list without the Users Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteList(38, null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.deleteList(38, "")
            }
        }
    }

    @Test
    fun `Delete a list without the list Id`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteList(null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Delete a list with Series in it`(){
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            val seriesLists = seriesServices.getLists("bearer ${user?.token.toString()}")

            assertEquals(seriesLists[0].name, "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            seriesServices.deleteList(seriesLists[0].id, "bearer ${user?.token.toString()}")

            val seriesList = seriesServices.getList(seriesLists[0].id, "bearer ${user?.token.toString()}")

            assertTrue(seriesList.isEmpty())
        }
    }
    @Test
    fun `Get all Lists for a User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.createList("bearer ${user?.token.toString()}", "Favourite Series")

            seriesServices.createList("bearer ${user?.token.toString()}", "Sitcoms")

            val seriesList = seriesServices.getLists("bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Fantasy Series")
            assertEquals(seriesList[1].name, "Favourite Series")
            assertEquals(seriesList[2].name, "Sitcoms")
        }
    }

    @Test
    fun `Get all Lists for a User without the User's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            assertFailsWith<BadRequestException> {
                seriesServices.getLists(null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.getLists("")
            }
        }
    }


    @Test
    fun `Add a Series to a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)
        }
    }

    @Test
    fun `Add a Series to a list without the series tmdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList(null,listId,"bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Add a Series to a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList(66732,null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Add a Series to a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList(66732,listId,null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList(66732,listId,"")
            }
        }
    }

    //TODO ver como vem da API externa
    @Test
    fun `Add a Series to a list but the Series doesn't exists`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            assertFailsWith<BadRequestException> {
                seriesServices.addSeriesToList(667325465,listId,"bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Delete a series from a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId,"bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.deleteSeriesFromList(listId, 66732, "bearer ${user?.token.toString()}")

            val list = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertTrue(list.isEmpty())
        }
    }

    @Test
    fun `Delete a series from a list without the list's Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(null, 66732, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Delete a series from a list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, 66732, null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, 66732, "")
            }
        }
    }

    @Test
    fun `Delete a series from a list without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, null, "bearer ${user?.token.toString()}")
            }
        }
    }

    //TODO ver como vem da DB
    @Test
    fun `Delete a series from a list but the series is not on the list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId,"bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.deleteSeriesFromList(listId, 641616518, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Change the state of a series in a list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.changeState(66732, "Watching","bearer ${user?.token.toString()}")

            val seriesState1 = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            assertEquals(seriesState1?.state, SeriesState.Watching)

            seriesServices.changeState(66732, "Watched", "bearer ${user?.token.toString()}")

            val seriesState2 = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            assertEquals(seriesState2?.state, SeriesState.Watched)
        }
    }

    @Test
    fun `Change the state of a series in a list without providing the state to change to`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId, "bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.changeState(66732, null, "bearer ${user?.token.toString()}")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.changeState(66732, "", "bearer ${user?.token.toString()}")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.changeState(66732, "Banana", "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Change the state of a series in a list without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.changeState(null, "Watching", "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Change the state of a series in a list without the users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")


            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.changeState(66732, "Watching", null)
            }

            assertFailsWith<BadRequestException>{
                seriesServices.changeState(66732, "Watching", "")
            }
        }
    }

    @Test
    fun `Get the Watched Episodes list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId,"bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            val wepList = seriesServices.getWatchedEpList(66732,"bearer ${user?.token.toString()}")

            assertTrue(wepList.isEmpty())
        }
    }

    @Test
    fun `Get the Watched Episodes list without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList( 66732, listId, "bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.getWatchedEpList(66732, null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.getWatchedEpList(66732, "")
            }
        }
    }

    @Test
    fun `Get the Watched Episodes list without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList( 66732, listId, "bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.getWatchedEpList(null, "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Add a watched episode to the Watched Episodes List`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1, "bearer ${user?.token.toString()}")

            val wepList = seriesServices.getWatchedEpList(66732,"bearer ${user?.token.toString()}")

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].epimdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)
        }
    }

    @Test
    fun `Add a watched episode to the Watched Episodes List without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,"")
            }
        }
    }

    @Test
    fun `Add a watched episode to the Watched Episodes List without the series tmdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(null,"tt4593118",1,1,"bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Add a watched episode to the Watched Episodes List without the episode imdbId, episode number and season number`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,null,1,1,"bearer ${user?.token.toString()}")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"",1,1,"bearer ${user?.token.toString()}")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",null,1,"bearer ${user?.token.toString()}")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,null,"bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Remove a Episode from the Watched Episodes List`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,"bearer ${user?.token.toString()}")

            val wepList = seriesServices.getWatchedEpList(66732, "bearer ${user?.token.toString()}")

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].epimdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            seriesServices.removeWatchedEpisode(66732,"tt4593118","bearer ${user?.token.toString()}")

            val wepList1 = seriesServices.getWatchedEpList(66732, "bearer ${user?.token.toString()}")

            assertTrue(wepList1.isEmpty())
        }
    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,"bearer ${user?.token.toString()}")

            val wepList = seriesServices.getWatchedEpList(66732, "bearer ${user?.token.toString()}")

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].epimdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode(66732,"tt4593118",null)
            }

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode(66732,"tt4593118", "")
            }
        }
    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the series Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732,listId,"bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId,"tt4593118",1,1,"bearer ${user?.token.toString()}")

            val wepList = seriesServices.getWatchedEpList(66732, "bearer ${user?.token.toString()}")

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name,"Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].epimdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode(null,"tt4593118","bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Remove a Episode from the Watched Episodes List without the episode imdbId`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732, listId, "bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId, "tt4593118", 1, 1, "bearer ${user?.token.toString()}")

            val wepList = seriesServices.getWatchedEpList(66732, "bearer ${user?.token.toString()}")

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name, "Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].epimdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode(66372,"", "bearer ${user?.token.toString()}")
            }
            assertFailsWith<BadRequestException> {
                seriesServices.removeWatchedEpisode(66732, null, "bearer ${user?.token.toString()}")
            }
        }
    }

    //TODO ver como ver da DB
    @Test
    fun `Remove a Episode from the Watched Episodes List but the episode is not in the list`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            seriesServices.addSeriesToList(66732, listId, "bearer ${user?.token.toString()}")

            val seriesData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesData(66732) }

            assertEquals(seriesData?.name, "Stranger Things")
            assertEquals(seriesData?.imdbId, "tt4574334")
            assertEquals(seriesData?.tmdbId, 66732)
            assertEquals(seriesData?.img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")

            val seriesUserData = transactionManager.run { it.seriesRepository.getSeriesFromSeriesUserData(66732, userId) }

            //TODO ver se é melhor criar outra class para Series User Data
            assertEquals(seriesUserData?.state, SeriesState.PTW)
            assertNotNull(seriesUserData?.epListId)

            val seriesList = seriesServices.getList(listId, "bearer ${user?.token.toString()}")

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.PTW)

            seriesServices.addWatchedEpisode(seriesList[0].tmdbId, "tt4593118", 1, 1, "bearer ${user?.token.toString()}")

            val wepList = seriesServices.getWatchedEpList(66732, "bearer ${user?.token.toString()}")

            assertEquals(wepList[0].episode, 1)
            assertEquals(wepList[0].season, 1)
            assertEquals(wepList[0].name, "Chapter One: The Vanishing of Will Byers")
            assertEquals(wepList[0].img, "/AdwF2jXvhdODr6gUZ61bHKRkz09.jpg")
            assertEquals(wepList[0].epimdbId, "tt4593118")
            assertEquals(wepList[0].seriesId, 66732)

            assertFailsWith<NotFoundException> {
                seriesServices.removeWatchedEpisode(66732, "tt4593118shbsry", "bearer ${user?.token.toString()}")
            }
        }
    }

    @Test
    fun `Get List of Series with the same State`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            val listId1 = seriesServices.createList("bearer ${user?.token.toString()}", "Favourite Series")

            val listId2 = seriesServices.createList("bearer ${user?.token.toString()}", "Action Series")

            seriesServices.addSeriesToList(66732, listId, "bearer ${user?.token.toString()}")

            seriesServices.addSeriesToList( 1402, listId1, "bearer ${user?.token.toString()}")

            seriesServices.addSeriesToList( 58841, listId2, "bearer ${user?.token.toString()}")

            seriesServices.changeState(1402,"Watched", "bearer ${user?.token.toString()}")

            seriesServices.changeState(66732, "Watching", "bearer ${user?.token.toString()}")

            val seriesList = seriesServices.getSeriesFromUserByState("bearer ${user?.token.toString()}", "Watching")

            assertEquals(seriesList.size, 1)

            assertEquals(seriesList[0].name, "Stranger Things")
            assertEquals(seriesList[0].imdbId, "tt4574334")
            assertEquals(seriesList[0].tmdbId, 66732)
            assertEquals(seriesList[0].img, "/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
            assertEquals(seriesList[0].state, SeriesState.Watching)
        }
    }

    @Test
    fun `Get List of Series with the same State without Users Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            val listId1 = seriesServices.createList("bearer ${user?.token.toString()}", "Favourite Series")

            val listId2 = seriesServices.createList("bearer ${user?.token.toString()}", "Action Series")

            seriesServices.addSeriesToList(66732, listId, "bearer ${user?.token.toString()}")

            seriesServices.addSeriesToList( 1402, listId1, "bearer ${user?.token.toString()}")

            seriesServices.addSeriesToList( 58841, listId2, "bearer ${user?.token.toString()}")

            seriesServices.changeState(1402,"Watched", "bearer ${user?.token.toString()}")

            seriesServices.changeState(66732, "Watching", "bearer ${user?.token.toString()}")

            assertFailsWith<BadRequestException> {
                seriesServices.getSeriesFromUserByState("", "Watching")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.getSeriesFromUserByState(null, "Watching")
            }
        }
    }

    @Test
    fun `Get List of Series with the same State without providing a state`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val tokenProcessor = TokenProcessor(usersServices)

            val seriesServices = SeriesServices(transactionManager, searchServices, tokenProcessor)

            val userId = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            val user = usersServices.getUserById(userId)

            val listId = seriesServices.createList("bearer ${user?.token.toString()}", "Fantasy Series")

            val listId1 = seriesServices.createList("bearer ${user?.token.toString()}", "Favourite Series")

            val listId2 = seriesServices.createList("bearer ${user?.token.toString()}", "Action Series")

            seriesServices.addSeriesToList(66732, listId, "bearer ${user?.token.toString()}")

            seriesServices.addSeriesToList( 1402, listId1, "bearer ${user?.token.toString()}")

            seriesServices.addSeriesToList( 58841, listId2, "bearer ${user?.token.toString()}")

            seriesServices.changeState(1402,"Watched", "bearer ${user?.token.toString()}")

            seriesServices.changeState(66732, "Watching", "bearer ${user?.token.toString()}")

            assertFailsWith<BadRequestException> {
                seriesServices.getSeriesFromUserByState("bearer ${user?.token.toString()}", "Banana")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.getSeriesFromUserByState("bearer ${user?.token.toString()}", "")
            }

            assertFailsWith<BadRequestException> {
                seriesServices.getSeriesFromUserByState("bearer ${user?.token.toString()}", null)
            }
        }
    }

}