package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.domain.content.SeriesListDetails
import com.example.cinescope.domain.content.UserDataContent
import okhttp3.Cookie

interface CinescopeSeriesServices {

    suspend fun addSeriesToList(seriesId: Int, listId: Int, cookie: Cookie)

    suspend fun changeSeriesState(seriesId: Int, state: String, cookie: Cookie)

    suspend fun deleteStateFromSeries(seriesId: Int, cookie: Cookie)

    suspend fun deleteSeriesFromList(seriesId: Int, listId: Int, cookie: Cookie)

    suspend fun addWatchedEpisode(seriesId: Int, seasonNr: Int, epNumber: Int, cookie: Cookie)

    suspend fun deleteSeriesList(listId: Int, cookie: Cookie)

    suspend fun deleteWatchedEpisode(seriesId: Int, seasonNr: Int, epNumber: Int, cookie: Cookie)

    suspend fun getAllSeriesByState(state: String, cookie: Cookie): List<SeriesData>

    suspend fun getAllSeriesLists(cookie: Cookie): List<ContentList>

    suspend fun getSeriesList(listId: Int, cookie: Cookie): SeriesListDetails

    suspend fun createSeriesList(name: String, cookie: Cookie): ListId

    suspend fun getSeriesUserData(seriesId: Int, cookie: Cookie): UserDataContent

    suspend fun getAllWatchedEpFromSeries(seriesId: Int, cookie: Cookie): List<EpisodeData>

}