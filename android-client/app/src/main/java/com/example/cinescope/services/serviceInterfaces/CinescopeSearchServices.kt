package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.searches.EpisodeInfo
import com.example.cinescope.domain.searches.SearchContent
import com.example.cinescope.domain.searches.Movie
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.domain.searches.Series
import com.example.cinescope.domain.searches.SeasonInfo
import com.example.cinescope.domain.searches.SeriesInfo

interface CinescopeSearchServices {
    suspend fun searchByQuery(searchQuery:String): SearchContent

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getPopularSeries(): List<Series>

    suspend fun getSeriesRecommendations(id:Int): List<Series>

    //TODO check if we will implement this functionalities
    suspend fun getMovieRecommendations(id:Int): List<Movie>

    suspend fun getMovieDetails(movieId: Int): MovieInfo

    suspend fun getSeriesDetails(seriesId: Int): SeriesInfo

    suspend fun getSeasonDetails(seriesId: Int, seasonNr: Int): SeasonInfo

    suspend fun getEpisodeDetails(seriesId: Int, seasonNr: Int, epNumber: Int): EpisodeInfo

}