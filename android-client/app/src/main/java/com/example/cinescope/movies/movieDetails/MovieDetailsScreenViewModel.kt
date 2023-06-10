package com.example.cinescope.movies.movieDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.services.cinescopeAPI.SearchServices
import kotlinx.coroutines.launch

class MovieDetailsScreenViewModel(private val searchServices: SearchServices):ViewModel() {
    private var loading by mutableStateOf(false)

    var movie by mutableStateOf<MovieInfo?>(null)
        private set

    var error by mutableStateOf<String?>(null)
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
}