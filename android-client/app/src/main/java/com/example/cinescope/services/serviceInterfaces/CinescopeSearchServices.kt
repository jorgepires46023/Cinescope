package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.searches.EpisodeInfo
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.domain.searches.SearchContent
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.domain.searches.SeasonInfo
import com.example.cinescope.domain.searches.SeriesInfo

interface CinescopeSearchServices {
    suspend fun searchByQuery(searchQuery:String): List<MediaContent>

    suspend fun getPopularMovies(): List<MediaContent>

    suspend fun getPopularSeries(): List<MediaContent>

    suspend fun getMovieDetails(movieId: Int): MovieInfo

    suspend fun getSeriesDetails(seriesId: Int): SeriesInfo

    suspend fun getSeasonDetails(seriesId: Int, seasonNr: Int): SeasonInfo

    suspend fun getEpisodeDetails(seriesId: Int, seasonNr: Int, epNumber: Int): EpisodeInfo

}