package com.example.cinescope.series.series

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.SeriesState
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import kotlinx.coroutines.launch
import okhttp3.Cookie
import java.lang.Exception

class SeriesViewModel(private val seriesServices: SeriesServices) : ViewModel(){
    var loading by mutableStateOf(false)
        private set

    var ptwList by mutableStateOf<List<SeriesData>?>(null)
        private set

    var watchingList by mutableStateOf<List<SeriesData>?>(null)
        private set

    var watchedList by mutableStateOf<List<SeriesData>?>(null)
        private set

    var error by mutableStateOf<String?>(null)

    //TODO change this getState functions by on that is requested when we choose the specific tab
    fun getPTWSeries(cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                ptwList = seriesServices.getAllSeriesByState(SeriesState.PTW.toString(), cookie)
            }catch (e: Exception){
                error = e.message
            }finally {
                loading = false
            }
        }
    }

    fun getWatchingSeries(cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                watchingList =
                    seriesServices.getAllSeriesByState(SeriesState.WATCHING.toString(), cookie)
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    fun getWatchedSeries(cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                watchedList =
                    seriesServices.getAllSeriesByState(SeriesState.WATCHED.toString(), cookie)
            } catch (e: Exception) {
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