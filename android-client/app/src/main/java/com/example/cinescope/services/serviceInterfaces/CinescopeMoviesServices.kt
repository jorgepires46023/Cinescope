package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.UserDataContent
import okhttp3.Cookie

interface CinescopeMoviesServices {
    suspend fun addMovieToList(movieId: Int, listId: Int, cookie: Cookie)

    suspend fun changeMovieState(movieId: Int, state: String, cookie: Cookie)

    suspend fun deleteStateFromMovie(movieId: Int, cookie: Cookie)

    suspend fun deleteMovieFromList(movieId: Int, listId: Int, cookie: Cookie)

    suspend fun deleteMoviesList(listId: Int, cookie: Cookie)

    suspend fun getAllMoviesByState(state: String, cookie: Cookie): List<MovieData>

    suspend fun getAllMoviesLists(cookie: Cookie): List<ContentList>

    suspend fun getMoviesList(listId: Int, cookie: Cookie): List<MovieData>

    suspend fun createMoviesList(name: String, cookie: Cookie): ListId

    suspend fun getMovieUserData(movieId: Int, cookie: Cookie): List<UserDataContent>

}