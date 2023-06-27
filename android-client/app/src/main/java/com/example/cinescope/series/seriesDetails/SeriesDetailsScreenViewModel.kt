package com.example.cinescope.series.seriesDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.MovieState
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.domain.searches.SeriesInfo
import com.example.cinescope.services.cinescopeAPI.SearchServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import kotlinx.coroutines.launch
import okhttp3.Cookie

class SeriesDetailsScreenViewModel(
    private val searchServices: SearchServices,
    private val seriesServices: SeriesServices
): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var series by mutableStateOf<SeriesInfo?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var userData by mutableStateOf<UserDataContent?>(null)
        private set

    var lists by mutableStateOf<List<ContentList>?>(null)
        private set

    fun getSeriesDetails(seriesId: Int){
        viewModelScope.launch {
            loading = true
            try {
                series = searchServices.getSeriesDetails(seriesId)
            }catch (e: Exception){
                error = e.message
            }finally {
                loading = false
            }
        }
    }

    fun getSeriesUserData(seriesId: Int, cookie: Cookie){
        viewModelScope.launch {
            loading = true
            try {
                userData = seriesServices.getSeriesUserData(seriesId, cookie)
            }catch (e: Exception){
                error = e.message
            }finally {
                loading = false
            }
        }
    }

    fun changeState(state: String, seriesId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                if(state == MovieState.NO_STATE.state)
                    seriesServices.deleteStateFromSeries(seriesId, cookie)
                else
                    seriesServices.changeSeriesState(seriesId, state, cookie)
            }catch (e: Exception){
                error = e.message
            }
        }
    }

    fun getLists(cookie: Cookie){
        viewModelScope.launch {
            try {
                lists = seriesServices.getAllSeriesLists(cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun addSeriesToList(listId: Int, movieId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                seriesServices.addSeriesToList(movieId, listId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun deleteSeriesFromList(listId: Int, movieId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                seriesServices.deleteSeriesFromList(movieId, listId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }


}