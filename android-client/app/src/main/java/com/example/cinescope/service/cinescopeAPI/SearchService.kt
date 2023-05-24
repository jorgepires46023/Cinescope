package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.service.dtoMappers.toMovies
import com.example.cinescope.service.dtos.PopularDto
import com.example.cinescope.service.exceptions.UnexpectedMappingException
import com.example.cinescope.service.exceptions.UnexpectedResponseException
import com.example.cinescope.service.serviceInterfaces.CinescopeSearchService
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.send
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.net.URL

class SearchService(
    cinescopeURL: URL
) : CinescopeSearchService, CinescopeService(cinescopeURL) {

    override suspend fun searchByQuery() {
        TODO("Not yet implemented")
    }

    override suspend fun movieDetails() {
        TODO("Not yet implemented")
    }

    override suspend fun seriesDetails() {
        TODO("Not yet implemented")
    }

    override suspend fun episodeDetails() {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(): List<Movie> {
        val popularMovies = mutableListOf<Movie>()
        val request = buildRequest(cinescopeURL.joinPath(Searches.GET_POPULAR_MOVIES))

        val popularMoviesDto = httpClient.send(request){response ->
            handleResponse<PopularDto>(response, PopularDto::class.java)
        }

        try {
             popularMovies.addAll(popularMoviesDto.toMovies())
        } catch (e: RuntimeException){
            throw UnexpectedMappingException()
        }

        return popularMovies
    }

    override suspend fun getPopularSeries(): List<Series> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieRecommendations() {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesRecommendations() {
        TODO("Not yet implemented")
    }
}
