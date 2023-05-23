package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.service.serviceInterfaces.CinescopeSearchService
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.send
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
            handleResponse<Movie>(response, Movie::class.java) //TODO change Movie for MovieDto and then map it to Movie
        }
        TODO("return list of popular movies")
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
