package com.example.cinescope.services


import com.example.cinescope.services.mockdata.searchContentObjWithEmptyLists
import com.example.cinescope.services.mockdata.emptyResponse
import com.example.cinescope.services.mockdata.expectedSearchContent
import com.example.cinescope.services.mockdata.expectedPopularMovies
import com.example.cinescope.services.mockdata.expectedPopularSeries
import com.example.cinescope.services.mockdata.expectedRecommendedMovies
import com.example.cinescope.services.mockdata.expectedRecommendedSeries
import com.example.cinescope.services.mockdata.movie1
import com.example.cinescope.services.mockdata.popMovieResponse
import com.example.cinescope.services.mockdata.popSeriesResponse
import com.example.cinescope.services.mockdata.recommendedMovieResponse
import com.example.cinescope.services.mockdata.recommendedSeriesResponse
import com.example.cinescope.services.mockdata.searchByQueryResponse
import com.example.cinescope.services.mockdata.series1
import com.example.cinescope.services.mockdata.wrongObjToMap
import com.example.cinescope.domain.searches.Movie
import com.example.cinescope.domain.searches.Series
import com.example.cinescope.services.cinescopeAPI.SearchServices
import com.example.cinescope.services.exceptions.UnexpectedResponseException
import com.example.cinescope.services.exceptions.UnsuccessfulResponseException
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
import java.net.URL

class SearchServicesTests {
    @get:Rule
    val testRule = MockWebServerRule()



    private val httpClient: OkHttpClient = OkHttpClient()
    private val gson: Gson= Gson()

    private val jsonFormatter: Gson = GsonBuilder().create()

    private val jsonMediaType = ("application/json").toMediaType()

    //GET POPULAR MOVIES TESTS
    @Test
    fun `getPopularMovies returns list of movies when response is understood`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( popMovieResponse))
            )

            //val cinescopeAPI = "http://localhost:9000"
            //val searchServices = SearchServices(URL(cinescopeAPI), gson, httpClient)
            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getPopularMovies()

            // Assert
            Assert.assertEquals(expectedPopularMovies, actual)
        }

    @Test(expected = UnsuccessfulResponseException::class)
    fun `getPopularMovies throws UnsuccessfulResponseException when the response is not expected`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse().setResponseCode(404)
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getPopularMovies()
        }

    @Test(expected = UnexpectedResponseException::class)
    fun `getPopularMovies throws UnexpectedResponseException when cannot understand response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( wrongObjToMap))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getPopularMovies()
        }

    @Test
    fun `getPopularMovies throws UnexpectedResponseException when empty response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( emptyResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getPopularMovies()

            //Assert
            Assert.assertEquals(emptyList<Movie>(), actual)
        }

    //GET POPULAR SERIES TESTS
    @Test
    fun `getPopularSeries returns list of movies when response is understood`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( popSeriesResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getPopularSeries()

            // Assert
            Assert.assertEquals(expectedPopularSeries, actual)
        }

    @Test(expected = UnsuccessfulResponseException::class)
    fun `getPopularSeries throws UnsuccessfulResponseException when the response is not expected`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse().setResponseCode(404)
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getPopularSeries()
        }

    @Test(expected = UnexpectedResponseException::class)
    fun `getPopularSeries throws UnexpectedResponseException when cannot understand response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( wrongObjToMap))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getPopularSeries()
        }

    @Test
    fun `getPopularSeries throws UnexpectedResponseException when empty response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( emptyResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getPopularSeries()

            //Assert
            Assert.assertEquals(emptyList<Series>(), actual)
        }

    //GET RECOMMENDED MOVIES TESTS
    @Test
    fun `getRecommendedMovies returns list of movies when response is understood`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( recommendedMovieResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getMovieRecommendations(movie1.movieId)

            // Assert
            Assert.assertEquals(expectedRecommendedMovies, actual)
        }

    @Test(expected = UnsuccessfulResponseException::class)
    fun `getRecommendedMovies throws UnsuccessfulResponseException when the response is not expected`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse().setResponseCode(404)
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getMovieRecommendations(movie1.movieId)
        }

    @Test(expected = UnexpectedResponseException::class)
    fun `getRecommendedMovies throws UnexpectedResponseException when cannot understand response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( wrongObjToMap))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getMovieRecommendations(movie1.movieId)
        }

    @Test
    fun `getRecommendedMovies throws UnexpectedResponseException when empty response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( emptyResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getMovieRecommendations(movie1.movieId)

            //Assert
            Assert.assertEquals(emptyList<Movie>(), actual)
        }

    //GET RECOMMENDED SERIES TESTS
    @Test
    fun `getRecommendedSeries returns list of movies when response is understood`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( recommendedSeriesResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getSeriesRecommendations(series1.seriesId)

            // Assert
            Assert.assertEquals(expectedRecommendedSeries, actual)
        }

    @Test(expected = UnsuccessfulResponseException::class)
    fun `getRecommendedSeries throws UnsuccessfulResponseException when the response is not expected`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse().setResponseCode(404)
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getSeriesRecommendations(series1.seriesId)
        }

    @Test(expected = UnexpectedResponseException::class)
    fun `getRecommendedSeries throws UnexpectedResponseException when cannot understand response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( wrongObjToMap))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.getSeriesRecommendations(series1.seriesId)
        }

    @Test
    fun `getRecommendedSeries throws UnexpectedResponseException when empty response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( emptyResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.getSeriesRecommendations(series1.seriesId)

            //Assert
            Assert.assertEquals(emptyList<Series>(), actual)
        }

    //GET SEARCH BY QUERY TESTS
    @Test
    fun `searchByQuery returns list of movies when response is understood`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( searchByQueryResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.searchByQuery("movieNameExample")

            // Assert
            Assert.assertEquals(expectedSearchContent, actual)
        }

    @Test(expected = UnsuccessfulResponseException::class)
    fun `searchByQuery throws UnsuccessfulResponseException when the response is not expected`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse().setResponseCode(404)
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.searchByQuery("movieNameExample")
        }

    @Test(expected = UnexpectedResponseException::class)
    fun `searchByQuery throws UnexpectedResponseException when cannot understand response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( wrongObjToMap))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            searchServices.searchByQuery("movieNameExample")
        }

    @Test
    fun `searchByQuery throws UnexpectedResponseException when empty response`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( emptyResponse))
            )

            val searchServices = SearchServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = searchServices.searchByQuery("movieNameExample")

            //Assert
            Assert.assertEquals(searchContentObjWithEmptyLists, actual)
        }
}