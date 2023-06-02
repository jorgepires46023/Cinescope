package com.example.cinescope.cinescopeservicetests

import com.example.cinescope.cinescopeservicetests.mockdata.createMoviesListResponse
import com.example.cinescope.cinescopeservicetests.mockdata.expectedCreateMoviesList
import com.example.cinescope.cinescopeservicetests.mockdata.expectedGetAllMoviesByState
import com.example.cinescope.cinescopeservicetests.mockdata.expectedGetAllMoviesLists
import com.example.cinescope.cinescopeservicetests.mockdata.expectedGetMovieUserData
import com.example.cinescope.cinescopeservicetests.mockdata.expectedGetMoviesList
import com.example.cinescope.cinescopeservicetests.mockdata.fakeListId
import com.example.cinescope.cinescopeservicetests.mockdata.fakeListName
import com.example.cinescope.cinescopeservicetests.mockdata.fakeMovieId
import com.example.cinescope.cinescopeservicetests.mockdata.fakeToken
import com.example.cinescope.cinescopeservicetests.mockdata.getAllMoviesByStateResponse
import com.example.cinescope.cinescopeservicetests.mockdata.getAllMoviesListsResponse
import com.example.cinescope.cinescopeservicetests.mockdata.getMovieUserDataResponse
import com.example.cinescope.cinescopeservicetests.mockdata.getMoviesListResponse
import com.example.cinescope.domain.MovieState
import com.example.cinescope.services.cinescopeAPI.MoviesServices
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

class MoviesServicesTests {
    @get:Rule
    val testRule = MockWebServerRule()

    private val httpClient: OkHttpClient = OkHttpClient()
    private val gson: Gson = Gson()

    private val jsonFormatter: Gson = GsonBuilder().create()

    private val jsonMediaType = ("application/json").toMediaType()

    //ADD MOVIE TO LIST TESTS
    @Test
    fun `addMovieToList doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            movieServices.addMovieToList(fakeMovieId, fakeListId, fakeToken)
        }

    //CHANGE MOVIE STATE TESTS
    @Test
    fun `changeMovieState doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            movieServices.changeMovieState(fakeMovieId, MovieState.WATCHED.state, fakeToken)
        }

    //DELETE STATE FROM MOVIE TESTS
    @Test
    fun `deleteStateFromMovie doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            movieServices.deleteStateFromMovie(fakeMovieId, fakeToken)
        }

    //DELETE MOVIE FROM LIST TESTS
    @Test
    fun `deleteMovieFromList doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            movieServices.deleteMovieFromList(fakeMovieId, fakeListId, fakeToken)
        }

    //DELETE MOVIES LIST TESTS
    @Test
    fun `deleteMovielist doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            movieServices.deleteMoviesList(fakeListId, fakeToken)
        }

    //GET ALL MOVIES BY STATE TESTS
    @Test
    fun `getAllMoviesByState returns List of MovieData when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getAllMoviesByStateResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = movieServices.getAllMoviesByState(MovieState.WATCHED.state, fakeToken)

            // Assert
            Assert.assertEquals(expectedGetAllMoviesByState, actual)
        }

    //GET ALL MOVIES LISTS TESTS
    @Test
    fun `getAllMoviesLists returns List of ContentList when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getAllMoviesListsResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = movieServices.getAllMoviesLists(fakeToken)

            // Assert
            Assert.assertEquals(expectedGetAllMoviesLists, actual)
        }

    //GET MOVIES LIST TESTS
    @Test
    fun `getMoviesList returns List of MovieData when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getMoviesListResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = movieServices.getMoviesList(fakeListId, fakeToken)

            // Assert
            Assert.assertEquals(expectedGetMoviesList, actual)
        }

    //CREATE MOVIES LIST TESTS
    @Test
    fun `createMoviesList returns ListId when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(createMoviesListResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = movieServices.createMoviesList(fakeListName, fakeToken)

            // Assert
            Assert.assertEquals(expectedCreateMoviesList, actual)
        }

    //GET MOVIE USER DATA TESTS
    @Test
    fun `getMovieUserData returns List of UserDataContent when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson(getMovieUserDataResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = movieServices.getMovieUserData(fakeMovieId, fakeToken)

            // Assert
            Assert.assertEquals(expectedGetMovieUserData, actual)
        }
}