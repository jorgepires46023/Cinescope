package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EmptyData
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.services.dtos.MovieUserData

interface CinescopeMoviesService {
    suspend fun addMovieToList(movieId: Int, listId: Int, token: String): EmptyData

    suspend fun changeMovieState(movieId: Int, state: String, token: String): EmptyData

    suspend fun deleteStateFromMovie(movieId: Int, token: String): EmptyData

    suspend fun deleteMovieFromList(movieId: Int, listId: Int, token: String): EmptyData

    suspend fun deleteMoviesList(listId: Int, token: String): EmptyData

    suspend fun getAllMoviesByState(state: String, token: String): List<MovieData>

    suspend fun getAllMoviesLists(token: String): List<ContentList>

    suspend fun getMoviesList(listId: Int, token: String): List<MovieData>

    suspend fun createMoviesList(name: String, token: String): ListId

    suspend fun getMovieUserData(movieId: String, token: String): List<UserDataContent>

}