package com.example.cinescope.service

import com.example.cinescope.domain.Series

interface CinescopeSeriesService {

    suspend fun addSeriesToList(seriesId: Int, listId: Int): Series

    suspend fun changeSeriesState(seriesId: Int, listId: Int): Series

    suspend fun getSeriesByState(seriesId: Int, listId: Int): Series

    suspend fun addWatchedEpisode(seriesId: Int, listId: Int): Series

    suspend fun removeWatchedEpisode(seriesId: Int, listId: Int): Series

    suspend fun getWatchedEpList(seriesId: Int, listId: Int): Series

    suspend fun getSeriesLists(seriesId: Int, listId: Int): Series

    suspend fun getSeriesList(seriesId: Int, listId: Int): Series

    suspend fun createSeriesList(seriesId: Int, listId: Int): Series

    suspend fun deleteSeriesList(seriesId: Int, listId: Int): Series

    suspend fun deleteSeriesFromList(seriesId: Int, listId: Int): Series

    suspend fun removeSeriesState(seriesId: Int, listId: Int): Series

}