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
    fun getSeriesByState(state: String,cookie: Cookie){
        viewModelScope.launch {
            try {
                loading = true
                when(state){
                    SeriesState.PTW.state -> ptwList = seriesServices.getAllSeriesByState(state, cookie)
                    SeriesState.WATCHING.state -> watchingList = seriesServices.getAllSeriesByState(state, cookie)
                    SeriesState.WATCHED.state -> watchedList = seriesServices.getAllSeriesByState(state, cookie)
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