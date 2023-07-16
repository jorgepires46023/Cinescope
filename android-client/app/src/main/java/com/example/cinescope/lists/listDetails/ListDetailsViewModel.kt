package com.example.cinescope.lists.listDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.content.MovieListDetails
import com.example.cinescope.domain.content.SeriesListDetails
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import kotlinx.coroutines.launch
import okhttp3.Cookie

class ListDetailsViewModel(
    private val moviesServices: MoviesServices,
    private val seriesServices: SeriesServices
): ViewModel(){

    var loading by mutableStateOf(false)
        private set

    var moviesList by mutableStateOf<MovieListDetails?>(null)
        private set

    var seriesList by mutableStateOf<SeriesListDetails?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun getListDetails(listId: Int, type: String, cookie: Cookie) {
        viewModelScope.launch {
            loading = true
            try {
                if (type == "Movies") {
                    moviesList = moviesServices.getMoviesList(listId, cookie)
                }

                if (type == "Series") {
                    seriesList = seriesServices.getSeriesList(listId, cookie)
                }
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun deleteContentFromList(listId: Int, id: Int, type: String, cookie: Cookie) {
        viewModelScope.launch {
            try {
                if (type == "Movies") {
                    moviesServices.deleteMovieFromList(id, listId, cookie)
                }

                if (type == "Series") {
                    seriesServices.deleteSeriesFromList(id, listId, cookie)
                }
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun clearError(){
        error = null
    }
}