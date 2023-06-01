package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.services.dtos.MovieUserData

interface CinescopeMoviesService {

    suspend fun addMovieToList(movieId: Int, listId: Int, token: String)

    suspend fun createMoviesList(name: String, token: String)

    suspend fun changeMovieState(movieId: Int, state: String, token: String)

    suspend fun getListsByState(state: String, token: String): List<MovieUserData>

    suspend fun getMoviesList(movieId: Int, listId: Int, token: String)

    suspend fun deleteMoviesList(movieId: Int, listId: Int, token: String)

    suspend fun deleteMovieFromList(movieId: Int, listId: Int, token: String)

    suspend fun deleteStateFromMovie(movieId: Int, listId: Int, token: String)

}