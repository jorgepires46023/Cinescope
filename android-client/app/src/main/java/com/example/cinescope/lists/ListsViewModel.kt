package com.example.cinescope.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import kotlinx.coroutines.launch
import okhttp3.Cookie

class ListsViewModel(
    private val moviesServices: MoviesServices,
    private val seriesServices: SeriesServices
): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var moviesLists by mutableStateOf<List<ContentList>?>(null)
        private set
    var seriesLists by mutableStateOf<List<ContentList>?>(null)
        private set

    var error by mutableStateOf<String?>(null)

    fun getMoviesLists(cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                moviesLists = moviesServices.getAllMoviesLists(cookie)
            } catch(e: Exception){
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun getSeriesLists(cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                seriesLists = seriesServices.getAllSeriesLists(cookie)
            } catch(e: Exception){
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun createMovieList(name: String, cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                moviesServices.createMoviesList(name, cookie)
            } catch(e: Exception){
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun createSeriesList(name: String, cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                seriesServices.createSeriesList(name, cookie)
            } catch(e: Exception){
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