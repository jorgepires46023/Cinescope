package com.example.cinescope.series.seriesDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.searches.SeriesInfo
import com.example.cinescope.services.cinescopeAPI.SearchServices
import kotlinx.coroutines.launch

class SeriesDetailsScreenViewModel(private val searchServices: SearchServices): ViewModel() {
    private var loading by mutableStateOf(false)

    var series by mutableStateOf<SeriesInfo?>(null)
        private set

    var error by mutableStateOf<String?>(null)
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
}