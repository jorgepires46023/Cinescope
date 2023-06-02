package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EmptyData
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.domain.content.UserDataContent

interface CinescopeSeriesServices {

    suspend fun addSeriesToList(seriesId: Int, listId: Int, token: String): EmptyData

    suspend fun changeSeriesState(seriesId: Int, state: String, token: String): EmptyData

    suspend fun deleteStateFromSeries(seriesId: Int, token: String): EmptyData

    suspend fun deleteSeriesFromList(seriesId: Int, listId: Int, token: String): EmptyData

    suspend fun addWatchedEpisode(seriesId: Int, seasonNr: Int, epNumber: Int, token: String): EmptyData

    suspend fun deleteSeriesList(listId: Int, token: String): EmptyData

    suspend fun deleteWatchedEpisode(seriesId: Int, seasonNr: Int, epNumber: Int, token: String): EmptyData

    suspend fun getAllSeriesByState(state: String, token: String): List<SeriesData>

    suspend fun getAllSeriesLists(token: String): List<ContentList>

    suspend fun getSeriesList(listId: Int, token: String): List<SeriesData>

    suspend fun createSeriesList(name: String, token: String): ListId

    suspend fun getSeriesUserData(seriesId: Int, token: String): List<UserDataContent>

    suspend fun getAllWatchedEpFromSeries(seriesId: Int, token: String): List<EpisodeData>

}