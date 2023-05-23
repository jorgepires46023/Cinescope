package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.domain.Movie
import com.example.cinescope.service.serviceInterfaces.CinescopeMoviesService

class MoviesService: CinescopeMoviesService {
    override suspend fun addMovieToList(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun createMoviesList(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun changeMovieState(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun getListsByState(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesList(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMoviesList(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovieFromList(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStateFromMovie(movieId: Int, listId: Int): Movie {
        TODO("Not yet implemented")
    }
}