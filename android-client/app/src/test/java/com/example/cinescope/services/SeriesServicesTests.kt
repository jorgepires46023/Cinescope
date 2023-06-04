package com.example.cinescope.services

import com.example.cinescope.services.mockdata.createSeriesListResponse
import com.example.cinescope.services.mockdata.expectedCreateSeriesList
import com.example.cinescope.services.mockdata.expectedGetAllSeriesByState
import com.example.cinescope.services.mockdata.expectedGetAllSeriesLists
import com.example.cinescope.services.mockdata.expectedGetAllWatchedEpFromSeries
import com.example.cinescope.services.mockdata.expectedGetSeriesList
import com.example.cinescope.services.mockdata.expectedGetSeriesUserData
import com.example.cinescope.services.mockdata.fakeEpisodeNr
import com.example.cinescope.services.mockdata.fakeSeasonNr
import com.example.cinescope.services.mockdata.fakeSeriesId
import com.example.cinescope.services.mockdata.fakeSeriesListId
import com.example.cinescope.services.mockdata.fakeSeriesListName
import com.example.cinescope.services.mockdata.fakeSeriesState
import com.example.cinescope.services.mockdata.fakeUserToken
import com.example.cinescope.services.mockdata.getAllSeriesByStateResponse
import com.example.cinescope.services.mockdata.getAllSeriesListsResponse
import com.example.cinescope.services.mockdata.getAllWatchedEpFromSeriesResponse
import com.example.cinescope.services.mockdata.getSeriesListResponse
import com.example.cinescope.services.mockdata.getSeriesUserDataResponse
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import com.example.cinescope.testutils.MockWebServerRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SeriesServicesTests {
    @get:Rule
    val testRule = MockWebServerRule()

    private val httpClient: OkHttpClient = OkHttpClient()
    private val gson: Gson = Gson()

    private val jsonFormatter: Gson = GsonBuilder().create()

    private val jsonMediaType = ("application/json").toMediaType()

    //ADD SERIES TO LIST TESTS
    @Test
    fun `addSeriesToList doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            seriesServices.addSeriesToList(fakeSeriesId, fakeSeriesListId, fakeUserToken)
        }

    //CHANGE SERIES STATE TESTS
    @Test
    fun `changeSeriesState doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            seriesServices.changeSeriesState(fakeSeriesId, fakeSeriesState, fakeUserToken)
        }

    //DELETE STATE FROM SERIES TESTS
    @Test
    fun `deleteStateFromSeries doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            seriesServices.deleteStateFromSeries(fakeSeriesId, fakeUserToken)
        }

    //DELETE SERIES FROM LIST TESTS
    @Test
    fun `deleteSeriesFromList doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            seriesServices.deleteSeriesFromList(fakeSeriesId, fakeSeriesListId, fakeUserToken)
        }

    //ADD WATCHED EPISODE TESTS
    @Test
    fun `addWatchedEpisode doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            seriesServices.addWatchedEpisode(fakeSeriesId, fakeSeasonNr, fakeEpisodeNr, fakeUserToken)
        }

    //DELETE SERIES LIST TESTS
    @Test
    fun `deleteSeriesList doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            seriesServices.deleteSeriesList(fakeSeriesListId, fakeUserToken)
        }

    //DELETE WATCHED EPISODE TESTS
    @Test
    fun `deleteWatchedEpisode doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            seriesServices.deleteWatchedEpisode(fakeSeriesId, fakeSeasonNr, fakeEpisodeNr, fakeUserToken)
        }

    //GET ALL SERIES BY STATE TESTS
    @Test
    fun `getAllSeriesByState returns List of ContentList when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getAllSeriesByStateResponse))
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = seriesServices.getAllSeriesByState(fakeSeriesState, fakeUserToken)

            // Assert
            Assert.assertEquals(expectedGetAllSeriesByState, actual)
        }

    //GET ALL SERIES LISTS TESTS
    @Test
    fun `getAllSeriesLists returns List of ContentList when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getAllSeriesListsResponse))
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = seriesServices.getAllSeriesLists(fakeUserToken)

            // Assert
            Assert.assertEquals(expectedGetAllSeriesLists, actual)
        }

    //GET SERIES LIST TESTS
    @Test
    fun `getSeriesList returns List of ContentList when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getSeriesListResponse))
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = seriesServices.getSeriesList(fakeSeriesListId, fakeUserToken)

            // Assert
            Assert.assertEquals(expectedGetSeriesList, actual)
        }

    //CREATE SERIES LIST TESTS
    @Test
    fun `createSeriesList returns List of ContentList when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(createSeriesListResponse))
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = seriesServices.createSeriesList(fakeSeriesListName, fakeUserToken)

            // Assert
            Assert.assertEquals(expectedCreateSeriesList, actual)
        }

    //GET SERIES USER DATA TESTS
    @Test
    fun `getSeriesUserData returns List of ContentList when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getSeriesUserDataResponse))
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = seriesServices.getSeriesUserData(fakeSeriesId, fakeUserToken)

            // Assert
            Assert.assertEquals(expectedGetSeriesUserData, actual)
        }

    //GET ALL WATCHED EPISODES FROM SERIES TESTS
    @Test
    fun `getAllWatchedEpFromSeries returns List of ContentList when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getAllWatchedEpFromSeriesResponse))
            )

            val seriesServices = SeriesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = seriesServices.getAllWatchedEpFromSeries(fakeSeriesId, fakeUserToken)

            // Assert
            Assert.assertEquals(expectedGetAllWatchedEpFromSeries, actual)
        }

}