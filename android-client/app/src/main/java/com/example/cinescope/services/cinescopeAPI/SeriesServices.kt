package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EmptyData
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.serviceInterfaces.CinescopeSeriesServices
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.net.URL

class SeriesServices(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeSeriesServices, CinescopeServices(gson, httpClient) {
    override suspend fun addSeriesToList(seriesId: Int, listId: Int, token: String): EmptyData {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.ADD_SERIE, listOf(seriesId.toString(),listId.toString())),
            method = MethodHTTP.POST,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, EmptyData::class.java)
        }
    }

    override suspend fun changeSeriesState(seriesId: Int, state: String, token: String): EmptyData {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStateFromSeries(seriesId: Int, token: String): EmptyData {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSeriesFromList(
        seriesId: Int,
        listId: Int,
        token: String
    ): EmptyData {
        TODO("Not yet implemented")
    }

    override suspend fun addWatchedEpisode(
        seriesId: Int,
        seasonNr: Int,
        epNumber: Int,
        token: String
    ): EmptyData {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSeriesList(seriesId: Int, token: String): EmptyData {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWatchedEpisode(
        seriesId: Int,
        seasonNr: Int,
        epNumber: Int,
        token: String
    ): EmptyData {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSeriesByState(state: String, token: String): List<SeriesData> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSeriesLists(token: String): List<ContentList> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesList(listId: Int, token: String): List<SeriesData> {
        TODO("Not yet implemented")
    }

    override suspend fun createSeriesList(name: String, token: String): ListId {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesUserData(seriesId: Int, token: String): List<UserDataContent> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWatchedEpFromSeries(
        seriesId: Int,
        token: String
    ): List<EpisodeData> {
        TODO("Not yet implemented")
    }

}