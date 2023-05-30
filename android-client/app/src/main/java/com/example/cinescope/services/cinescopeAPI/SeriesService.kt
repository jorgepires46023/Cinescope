package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.Series
import com.example.cinescope.services.serviceInterfaces.CinescopeMoviesService
import com.example.cinescope.services.serviceInterfaces.CinescopeSeriesService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.net.URL

class SeriesService(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeSeriesService, CinescopeService(gson, httpClient) {
    override suspend fun addSeriesToList(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun changeSeriesState(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesByState(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun addWatchedEpisode(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun removeWatchedEpisode(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun getWatchedEpList(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesLists(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesList(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun createSeriesList(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSeriesList(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSeriesFromList(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }

    override suspend fun removeSeriesState(seriesId: Int, listId: Int): Series {
        TODO("Not yet implemented")
    }
}