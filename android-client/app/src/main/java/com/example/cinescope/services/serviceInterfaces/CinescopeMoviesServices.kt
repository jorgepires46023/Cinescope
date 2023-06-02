package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.UserDataContent

interface CinescopeMoviesServices {
    suspend fun addMovieToList(movieId: Int, listId: Int, token: String)

    suspend fun changeMovieState(movieId: Int, state: String, token: String)

    suspend fun deleteStateFromMovie(movieId: Int, token: String)

    suspend fun deleteMovieFromList(movieId: Int, listId: Int, token: String)

    suspend fun deleteMoviesList(listId: Int, token: String)

    suspend fun getAllMoviesByState(state: String, token: String): List<MovieData>

    suspend fun getAllMoviesLists(token: String): List<ContentList>

    suspend fun getMoviesList(listId: Int, token: String): List<MovieData>

    suspend fun createMoviesList(name: String, token: String): ListId

    suspend fun getMovieUserData(movieId: Int, token: String): List<UserDataContent>

}