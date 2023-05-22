package com.example.cinescope.service

import com.example.cinescope.domain.Movie

interface CinescopeMoviesService {

    suspend fun addMovieToList(movieId: Int, listId: Int): Movie

    suspend fun createMoviesList(movieId: Int, listId: Int): Movie

    suspend fun changeMovieState(movieId: Int, listId: Int): Movie

    suspend fun getListsByState(movieId: Int, listId: Int): Movie

    suspend fun getMoviesList(movieId: Int, listId: Int): Movie

    suspend fun deleteMoviesList(movieId: Int, listId: Int): Movie

    suspend fun deleteMovieFromList(movieId: Int, listId: Int): Movie

    suspend fun deleteStateFromMovie(movieId: Int, listId: Int): Movie

}