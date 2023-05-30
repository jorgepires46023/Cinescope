package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.CompleteSearch
import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.services.dtoMappers.toContent
import com.example.cinescope.services.dtoMappers.toMovies
import com.example.cinescope.services.dtoMappers.toSeries
import com.example.cinescope.services.dtos.ContentAPIDto
import com.example.cinescope.services.dtos.EpisodeInfo
import com.example.cinescope.services.dtos.MovieInfo
import com.example.cinescope.services.dtos.SeriesInfo
import com.example.cinescope.services.exceptions.UnexpectedMappingException
import com.example.cinescope.services.serviceInterfaces.CinescopeSearchService
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.lang.RuntimeException
import java.net.URL

class SearchService(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeSearchService, CinescopeService(gson, httpClient) {

    override suspend fun searchByQuery(searchQuery:String): CompleteSearch {
        val request = buildRequest(cinescopeURL
            .joinPathWithVariables(Searches.SEARCH_QUERY, listOf(searchQuery) ))

        val searchContentDto = httpClient.send(request) {response ->
            handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
        }

        try {
            return searchContentDto.toContent()
        } catch (e: RuntimeException){//TODO check if this is RuntimeException
            throw UnexpectedMappingException()
        }
    }

    override suspend fun movieDetails(id: Int): MovieInfo {
        val request = buildRequest(cinescopeURL
            .joinPathWithVariables(Searches.MOVIE_DETAILS, listOf(id.toString()) ))
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){response ->
            handleResponse(response, MovieInfo::class.java)
        }
    }

    override suspend fun seriesDetails(id: Int): SeriesInfo {
        val request = buildRequest(cinescopeURL
            .joinPathWithVariables(Searches.SERIE_DETAILS, listOf(id.toString()) ))
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){response ->
            handleResponse(response, SeriesInfo::class.java)
        }
    }

    override suspend fun episodeDetails(id: Int, seasonNumber: Int, epNumber: Int): EpisodeInfo {
        val request = buildRequest(cinescopeURL
            .joinPathWithVariables(Searches.MOVIE_RECOMMENDATIONS,
                listOf(id.toString(), seasonNumber.toString(), epNumber.toString())))

        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){response ->
            handleResponse(response, EpisodeInfo::class.java)
        }
    }

    override suspend fun getPopularMovies(): List<Movie> {
        val popularMovies = mutableListOf<Movie>()
        val request = buildRequest(cinescopeURL.joinPath(Searches.GET_POPULAR_MOVIES))

        //TODO handle this exceptions with our errors(try-catch)
        val popularMoviesDto = httpClient.send(request){response ->
            handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
        }

        try {
             popularMovies.addAll(popularMoviesDto.toMovies())
        } catch (e: RuntimeException){//TODO check if this is RuntimeException
            throw UnexpectedMappingException()
        }

        return popularMovies
    }

    override suspend fun getPopularSeries(): List<Series> {
        val popularSeries = mutableListOf<Series>()
        val request = buildRequest(cinescopeURL.joinPath(Searches.GET_POPULAR_SERIES))

        //TODO handle this exceptions with our errors(try-catch)
        val popularSeriesDto = httpClient.send(request){ response ->
            handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
        }

        try {
            popularSeries.addAll(popularSeriesDto.toSeries())
        } catch (e: RuntimeException){//TODO check if this is RuntimeException
            throw UnexpectedMappingException()
        }

        return popularSeries
    }

    override suspend fun getMovieRecommendations(id:Int): List<Movie> {
        val recommendedMovies = mutableListOf<Movie>()
        val request = buildRequest(cinescopeURL.joinPathWithVariables(Searches.MOVIE_RECOMMENDATIONS, listOf(id.toString())))


        //TODO handle this exceptions with our errors(try-catch)
        val recommendedMoviesDto = httpClient.send(request){ response ->
            handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
        }

        try {
            recommendedMovies.addAll(recommendedMoviesDto.toMovies())
        } catch (e: RuntimeException){//TODO check if this is RuntimeException
            throw UnexpectedMappingException()
        }

        return recommendedMovies
    }

    override suspend fun getSeriesRecommendations(id:Int): List<Series> {
        val recommendedSeries = mutableListOf<Series>()
        val request = buildRequest(cinescopeURL.joinPathWithVariables(Searches.SERIE_RECOMMENDATIONS, listOf(id.toString())))

        //TODO handle this exceptions with our errors(try-catch)
        val recommendedSeriesDto = httpClient.send(request){ response ->
            handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
        }

        try {
            recommendedSeries.addAll(recommendedSeriesDto.toSeries())
        } catch (e: RuntimeException){//TODO check if this is RuntimeException
            throw UnexpectedMappingException()
        }

        return recommendedSeries
    }
}
