package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.searches.SearchContent
import com.example.cinescope.domain.searches.Movie
import com.example.cinescope.domain.searches.Series
import com.example.cinescope.services.dtos.EpisodeInfo
import com.example.cinescope.services.dtos.MovieInfo
import com.example.cinescope.services.dtos.SeriesInfo

interface CinescopeSearchService {
    suspend fun searchByQuery(searchQuery:String): SearchContent

    suspend fun movieDetails(id: Int): MovieInfo

    suspend fun seriesDetails(id: Int): SeriesInfo

    suspend fun episodeDetails(id: Int, seasonNumber: Int, epNumber: Int): EpisodeInfo

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getPopularSeries(): List<Series>

    //TODO check if we will implement this functionalities
    suspend fun getMovieRecommendations(id:Int): List<Movie>

    suspend fun getSeriesRecommendations(id:Int): List<Series>
    
}