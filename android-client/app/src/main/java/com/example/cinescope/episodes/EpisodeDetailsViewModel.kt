package com.example.cinescope.episodes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.searches.EpisodeInfo
import com.example.cinescope.services.cinescopeAPI.SearchServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import kotlinx.coroutines.launch
import okhttp3.Cookie

class EpisodeDetailsViewModel(
    private val searchServices: SearchServices,
    private val seriesServices: SeriesServices
): ViewModel() {

    var loading by mutableStateOf(false)
        private set

    var episode by mutableStateOf<EpisodeInfo?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var watchedEpisode by mutableStateOf(false)
        private set

    fun getEpisodeDetails(seriesId: Int, seasonNr: Int, episodeNr: Int){
        viewModelScope.launch {
            loading = true
            try {
                episode = searchServices.getEpisodeDetails(seriesId, seasonNr, episodeNr)
            }catch (e: Exception){
                error = e.message
            }finally {
                loading = false
            }
        }
    }

    fun changeWatchState(state: Boolean){
        watchedEpisode = state
    }

    fun addWatchedEpisode(seriesId: Int, seasonNr: Int, episodeNr: Int, cookie: Cookie) {
        viewModelScope.launch{
            try {
                seriesServices.addWatchedEpisode(seriesId, seasonNr, episodeNr, cookie)
                changeWatchState(true)
                watchedEpisode
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun deleteWatchedEpisode(seriesId: Int, seasonNr: Int, episodeNr: Int, cookie: Cookie) {
        viewModelScope.launch{
            try {
                seriesServices.deleteWatchedEpisode(seriesId, seasonNr, episodeNr, cookie)
                changeWatchState(false)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

}