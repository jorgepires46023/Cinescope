package com.example.cinescope.movies.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.MovieState
import com.example.cinescope.domain.SeriesState
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import kotlinx.coroutines.launch
import okhttp3.Cookie
import java.lang.Exception

class MoviesViewModel(private val moviesServices: MoviesServices): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var ptwList by mutableStateOf<List<MovieData>?>(null)
        private set

    var watchedList by mutableStateOf<List<MovieData>?>(null)
        private set

    var error by mutableStateOf<String?>(null)

    fun getMoviesByState(state: String,cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                when(state){
                    MovieState.PTW.state -> ptwList = moviesServices.getAllMoviesByState(state, cookie)
                    MovieState.WATCHED.state -> watchedList = moviesServices.getAllMoviesByState(state, cookie)
                    else -> error = "INVALID STATE PROVIDED"
                }
            }catch (e: Exception){
                error = e.message
            }finally {
                loading = false
            }
        }
    }

    fun clearError(){
        error = null
    }
}