package com.example.cinescope.trending

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.services.cinescopeAPI.SearchServices
import kotlinx.coroutines.launch

class TrendingViewModel(private val searchServices: SearchServices): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var popularMovies by mutableStateOf<List<MediaContent>?>(null)
        private set

    var popularSeries by mutableStateOf<List<MediaContent>?>(null)
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