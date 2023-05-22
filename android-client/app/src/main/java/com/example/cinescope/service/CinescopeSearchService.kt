package com.example.cinescope.service

import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series

interface CinescopeSearchService {

    suspend fun searchByQuery() //TODO -> Check this CineScope Method

    suspend fun movieDetails()

    suspend fun seriesDetails()

    suspend fun episodeDetails()

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getPopularSeries(): List<Series>

    //TODO check if we will implement this functionalities
    suspend fun getMovieRecommendations()

    suspend fun getSeriesRecommendations()
    
}