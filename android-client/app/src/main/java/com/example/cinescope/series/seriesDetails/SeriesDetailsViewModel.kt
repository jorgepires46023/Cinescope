package com.example.cinescope.series.seriesDetails

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.MovieState
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.SeasonDataState
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.domain.searches.SeasonInfo
import com.example.cinescope.domain.searches.SeriesInfo
import com.example.cinescope.services.cinescopeAPI.SearchServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import kotlinx.coroutines.launch
import okhttp3.Cookie


@SuppressLint("MutableCollectionMutableState")
class SeriesDetailsViewModel(
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

    var seasons by mutableStateOf<HashMap<Int, SeasonInfo>>(hashMapOf())
        private set

    var watchedEpisodes by mutableStateOf<List<EpisodeData>?>(null)

    //var episodesUserInfo by mutableStateOf<List<EpisodeUserInfo>?>(null)
        //private set

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

    fun addSeriesToList(listId: Int, seriesId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                seriesServices.addSeriesToList(seriesId, listId, cookie)
                userData = seriesServices.getSeriesUserData(seriesId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun deleteSeriesFromList(listId: Int, seriesId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
                seriesServices.deleteSeriesFromList(seriesId, listId, cookie)
                userData = seriesServices.getSeriesUserData(seriesId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun getSeasonDetails(seriesId: Int, seasonNr: Int){
        if(seasons[seasonNr] == null)
            viewModelScope.launch {
                loading = true
                try {
                    seasons[seasonNr] = searchServices.getSeasonDetails(seriesId, seasonNr)
                }catch (e: Exception) {
                    error = e.message
                }finally {
                    loading = false
                }
            }
    }

    fun getWatchedEpisodeList(seriesId: Int, cookie: Cookie){
        viewModelScope.launch {
            try {
               watchedEpisodes = seriesServices.getAllWatchedEpFromSeries(seriesId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun addWatchedEpisode(seriesId: Int, seasonNr: Int, episodeNr: Int, cookie: Cookie) {
        viewModelScope.launch{
            try {
                seriesServices.addWatchedEpisode(seriesId, seasonNr, episodeNr, cookie)
                getWatchedEpisodeList(seriesId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun deleteWatchedEpisode(seriesId: Int, seasonNr: Int, episodeNr: Int, cookie: Cookie) {
        viewModelScope.launch{
            try {
                seriesServices.deleteWatchedEpisode(seriesId, seasonNr, episodeNr, cookie)
                getWatchedEpisodeList(seriesId, cookie)
            }catch (e: Exception) {
                error = e.message
            }
        }
    }
}


/*
            seasons?.seasonDetails?.episodes?.forEach { episode ->
                    episodesInfo.add(EpisodeUserInfo(
                        name = episode.name,
                        epId = episode.id,
                        seasonNr = season,
                        episodeNr = episode.episodeNumber,
                        isWatched = watchedEpisodes.find { it.season == season && it.episode == episode.episodeNumber } != null
                    ))
                }
                episodesUserInfo = episodesInfo
 */