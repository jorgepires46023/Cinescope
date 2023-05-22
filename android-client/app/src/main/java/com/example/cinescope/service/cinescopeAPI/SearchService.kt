package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.service.CinescopeSearchService

class SearchService: CinescopeSearchService {
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
        TODO("Not yet implemented")
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