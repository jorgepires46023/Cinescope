package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.domain.Series
import com.example.cinescope.service.serviceInterfaces.CinescopeSeriesService

class SeriesService: CinescopeSeriesService {
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