package com.example.cinescope.service

import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.domain.User

interface CinescopeAPIService {

    /** PROFILE SERVICE ACTIONS **/

    suspend fun signUp(email: String, pwd: String): User

    suspend fun signIn(email: String, pwd: String): User

    suspend fun getUserInfo(userId: Int): User

    /** MOVIES SERVICE ACTIONS **/

    suspend fun addMovieToList(movieId: Int, listId: Int): Movie

    suspend fun createMoviesList(movieId: Int, listId: Int): Movie

    suspend fun changeMovieState(movieId: Int, listId: Int): Movie

    suspend fun getListsByState(movieId: Int, listId: Int): Movie

    suspend fun getMoviesList(movieId: Int, listId: Int): Movie

    suspend fun deleteMoviesList(movieId: Int, listId: Int): Movie

    suspend fun deleteMovieFromList(movieId: Int, listId: Int): Movie

    suspend fun deleteStateFromMovie(movieId: Int, listId: Int): Movie

    /** SERIES SERVICE ACTIONS **/

    suspend fun addSeriesToList(seriesId: Int, listId: Int): Series

    suspend fun changeSeriesState(seriesId: Int, listId: Int): Series

    suspend fun getSeriesByState(seriesId: Int, listId: Int): Series

    suspend fun addWatchedEpisode(seriesId: Int, listId: Int): Series

    suspend fun removeWatchedEpisode(seriesId: Int, listId: Int): Series

    suspend fun getWatchedEpList(seriesId: Int, listId: Int): Series

    suspend fun getSeriesLists(seriesId: Int, listId: Int): Series

    suspend fun getSeriesList(seriesId: Int, listId: Int): Series

    suspend fun createSeriesList(seriesId: Int, listId: Int): Series

    suspend fun deleteSeriesList(seriesId: Int, listId: Int): Series

    suspend fun deleteSeriesFromList(seriesId: Int, listId: Int): Series

    suspend fun removeSeriesState(seriesId: Int, listId: Int): Series

    /** SEARCH SERVICE ACTIONS **/

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