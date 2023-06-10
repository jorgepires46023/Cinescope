package com.example.cinescope.trending

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.searches.Movie
import com.example.cinescope.domain.searches.Series
import com.example.cinescope.services.cinescopeAPI.SearchServices
import kotlinx.coroutines.launch

private val TAG = "TrendingViewModel"
class TrendingScreenViewModel(private val searchServices: SearchServices): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var popularMovies by mutableStateOf<List<Movie>?>(null)
        private set

    var popularSeries by mutableStateOf<List<Series>?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set
    fun getPopularMovies(){
        viewModelScope.launch {
            loading = true
            try {
                popularMovies = searchServices.getPopularMovies()
            } catch(e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun getPopularSeries(){
        viewModelScope.launch {
            loading = true
            try {
                popularSeries = searchServices.getPopularSeries()
            } catch(e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun clearError(){
        error = null
    }
}