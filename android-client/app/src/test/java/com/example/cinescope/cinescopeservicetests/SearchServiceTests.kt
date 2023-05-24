package com.example.cinescope.cinescopeservicetests

import com.example.cinescope.cinescopeservicetests.mockdata.expectedPopularMovies
import com.example.cinescope.cinescopeservicetests.mockdata.popMovieResponse
import com.example.cinescope.cinescopeservicetests.mockdata.wrongObjToMap
import com.example.cinescope.service.cinescopeAPI.SearchService
import com.example.cinescope.service.exceptions.UnexpectedMappingException
import com.example.cinescope.service.exceptions.UnexpectedResponseException
import com.example.cinescope.service.exceptions.UnsuccessfulResponseException
import com.example.cinescope.testutils.MockWebServerRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SearchServiceTests {
    @get:Rule
    val testRule = MockWebServerRule()

    private val jsonFormatter: Gson = GsonBuilder().create()

    private val JsonMediaType = ("application/json").toMediaType()

    //TODO check if it's possible to throw UnexpectedMappingException

    @Test
    fun `getPopularMovies returns list of movies when response is understood`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", JsonMediaType)
                .setBody(jsonFormatter.toJson( popMovieResponse))
            )

            val searchService = SearchService(mockServer.url("/").toUrl())

            // Act
            val actualResp = searchService.getPopularMovies()

            // Assert
            Assert.assertEquals(expectedPopularMovies, actualResp)
        }

    @Test(expected = UnsuccessfulResponseException::class)
    fun `getPopularMovies throws UnsuccessfulResponseException when the response is not expected`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse().setResponseCode(404)
            )

            val searchService = SearchService(mockServer.url("/").toUrl())

            // Act
            searchService.getPopularMovies()
        }

    @Test(expected = UnexpectedResponseException::class)
    fun `getPopularMovies throws UnexpectedResponseException when cannot understand response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", JsonMediaType)
                .setBody(jsonFormatter.toJson( wrongObjToMap))
            )

            val searchService = SearchService(mockServer.url("/").toUrl())

            // Act
            searchService.getPopularMovies()
        }
}