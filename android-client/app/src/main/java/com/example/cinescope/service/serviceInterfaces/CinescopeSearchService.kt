package com.example.cinescope.service.serviceInterfaces

import com.example.cinescope.domain.CompleteSearch
import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series

interface CinescopeSearchService {
    suspend fun searchByQuery(searchQuery:String): CompleteSearch

    suspend fun movieDetails(id: Int)

    suspend fun seriesDetails(id: Int)

    suspend fun episodeDetails()

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getPopularSeries(): List<Series>

    //TODO check if we will implement this functionalities
    suspend fun getMovieRecommendations(id:Int): List<Movie>

    suspend fun getSeriesRecommendations(id:Int): List<Series>
    
}