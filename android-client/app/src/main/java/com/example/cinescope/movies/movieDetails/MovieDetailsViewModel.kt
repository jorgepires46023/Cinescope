package com.example.cinescope.movies.movieDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.MovieState
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import com.example.cinescope.services.cinescopeAPI.SearchServices
import kotlinx.coroutines.launch
import okhttp3.Cookie

class MovieDetailsViewModel(
    private val searchServices: SearchServices,
    private val moviesServices: MoviesServices
): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var movie by mutableStateOf<MovieInfo?>(null)
        private set

    var userData by mutableStateOf<UserDataContent?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var lists by mutableStateOf<List<ContentList>?>(null)
        private set

    fun getMovieDetails(movieId: Int){
        viewModelScope.launch {
            loading = true
            try {
                movie = searchServices.getMovieDetails(movieId)
            }catch (e: Exception){
                error = e.message
            }finally {
                loading = false
            }
        }
    }

   fun getMovieUserData(movieId: Int, cookie: Cookie){
       viewModelScope.launch {
           loading = true
           try {
               userData = moviesServices.getMovieUserData(movieId, cookie)
           }catch (e: Exception){
               error = e.message
           }finally {
               loading = false
           }
       }
   }

    fun changeState(state: String, movieId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                if(state == MovieState.NO_STATE.state)
                    moviesServices.deleteStateFromMovie(movieId, cookie)
                else
                    moviesServices.changeMovieState(movieId, state, cookie)
            }catch (e: Exception){
                error = e.message
            }
        }
    }

    fun getLists(cookie: Cookie){
        viewModelScope.launch {
            try {
                lists = moviesServices.getAllMoviesLists(cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun addMovieToList(listId: Int, movieId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                moviesServices.addMovieToList(movieId, listId, cookie)
                userData = moviesServices.getMovieUserData(movieId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun deleteMovieFromList(listId: Int, movieId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                moviesServices.deleteMovieFromList(movieId, listId, cookie)
                userData = moviesServices.getMovieUserData(movieId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun clearError(){
        error=null
    }
}