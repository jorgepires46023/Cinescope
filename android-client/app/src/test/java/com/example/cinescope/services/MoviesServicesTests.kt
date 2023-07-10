package com.example.cinescope.services

import com.example.cinescope.domain.MovieState
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import com.example.cinescope.services.mockdata.createMoviesListResponse
import com.example.cinescope.services.mockdata.expectedCreateMoviesList
import com.example.cinescope.services.mockdata.expectedGetAllMoviesByState
import com.example.cinescope.services.mockdata.expectedGetAllMoviesLists
import com.example.cinescope.services.mockdata.expectedGetMovieUserData
import com.example.cinescope.services.mockdata.expectedGetMoviesList
import com.example.cinescope.services.mockdata.fakeMoviesCookie
import com.example.cinescope.services.mockdata.fakeListId
import com.example.cinescope.services.mockdata.fakeListName
import com.example.cinescope.services.mockdata.fakeMovieId
import com.example.cinescope.services.mockdata.getAllMoviesByStateResponse
import com.example.cinescope.services.mockdata.getAllMoviesListsResponse
import com.example.cinescope.services.mockdata.getMovieUserDataResponse
import com.example.cinescope.services.mockdata.getMoviesListResponse
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
                .setHeader("Cookie", fakeMoviesCookie.toString())
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            if (fakeMoviesCookie != null) {
                movieServices.addMovieToList(fakeMovieId, fakeListId, fakeMoviesCookie)
            }
        }

    //CHANGE MOVIE STATE TESTS
    @Test
    fun `changeMovieState doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setHeader("Cookie", fakeMoviesCookie.toString())
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            if (fakeMoviesCookie != null) {
                movieServices.changeMovieState(fakeMovieId, MovieState.WATCHED.state, fakeMoviesCookie)
            }
        }

    //DELETE STATE FROM MOVIE TESTS
    @Test
    fun `deleteStateFromMovie doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setHeader("Cookie", fakeMoviesCookie.toString())
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            if (fakeMoviesCookie != null) {
                movieServices.deleteStateFromMovie(fakeMovieId, fakeMoviesCookie)
            }
        }

    //DELETE MOVIE FROM LIST TESTS
    @Test
    fun `deleteMovieFromList doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setHeader("Cookie", fakeMoviesCookie.toString())
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            if (fakeMoviesCookie != null) {
                movieServices.deleteMovieFromList(fakeMovieId, fakeListId, fakeMoviesCookie)
            }
        }

    //DELETE MOVIES LIST TESTS
    @Test
    fun `deleteMovielist doesn't return nothing or throw exceptions when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setHeader("Cookie", fakeMoviesCookie.toString())
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            if (fakeMoviesCookie != null) {
                movieServices.deleteMoviesList(fakeListId, fakeMoviesCookie)
            }
        }

    //GET ALL MOVIES BY STATE TESTS
    @Test
    fun `getAllMoviesByState returns List of MovieData when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setHeader("Cookie", fakeMoviesCookie.toString())
                .setBody(jsonFormatter.toJson(getAllMoviesByStateResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual =
                fakeMoviesCookie?.let { movieServices.getAllMoviesByState(MovieState.WATCHED.state, it) }

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
                .setHeader("Cookie", fakeMoviesCookie.toString())
                .setBody(jsonFormatter.toJson(getAllMoviesListsResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = fakeMoviesCookie?.let { movieServices.getAllMoviesLists(it) }

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
                .setHeader("Cookie", fakeMoviesCookie.toString())
                .setBody(jsonFormatter.toJson(getMoviesListResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = fakeMoviesCookie?.let { movieServices.getMoviesList(fakeListId, it) }

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
                .setHeader("Cookie", fakeMoviesCookie.toString())
                .setBody(jsonFormatter.toJson(createMoviesListResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = fakeMoviesCookie?.let { movieServices.createMoviesList(fakeListName, it) }

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
                .setHeader("Cookie", fakeMoviesCookie.toString())
                .setBody(jsonFormatter.toJson(getMovieUserDataResponse))
            )

            val movieServices = MoviesServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = fakeMoviesCookie?.let { movieServices.getMovieUserData(fakeMovieId, it) }

            // Assert
            Assert.assertEquals(expectedGetMovieUserData, actual)
        }
}