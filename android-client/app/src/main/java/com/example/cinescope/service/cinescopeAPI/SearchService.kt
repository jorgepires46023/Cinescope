package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.domain.CompleteSearch
import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.service.dtoMappers.toContent
import com.example.cinescope.service.dtoMappers.toMovies
import com.example.cinescope.service.dtoMappers.toSeries
import com.example.cinescope.service.dtos.ContentAPIDto
import com.example.cinescope.service.exceptions.UnexpectedMappingException
import com.example.cinescope.service.serviceInterfaces.CinescopeSearchService
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.send
import java.lang.RuntimeException
import java.net.URL

class SearchService(
    cinescopeURL: URL
) : CinescopeSearchService, CinescopeService(cinescopeURL) {

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

    override suspend fun movieDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun seriesDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun episodeDetails() {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(): List<Movie> {
        val popularMovies = mutableListOf<Movie>()
        val request = buildRequest(cinescopeURL.joinPath(Searches.GET_POPULAR_MOVIES))

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
