package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.dtosMapping.ListEpisodeData
import com.example.cinescope.services.dtosMapping.ListOfContentList
import com.example.cinescope.services.dtosMapping.ListOfUserDataContent
import com.example.cinescope.services.dtosMapping.ListSeriesData
import com.example.cinescope.services.serviceInterfaces.CinescopeSeriesServices
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import java.net.URL

class SeriesServices(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeSeriesServices, CinescopeServices(gson, httpClient) {
    override suspend fun addSeriesToList(seriesId: Int, listId: Int, token: String) {
        val body = FormBody.Builder().build()
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.ADD_SERIE, listOf(seriesId.toString(),listId.toString())),
            method = MethodHTTP.POST,
            body = body,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun changeSeriesState(seriesId: Int, state: String, token: String) {
        val body = FormBody.Builder()
            .add("state", state)
            .build()
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.CHANGE_STATE, listOf(seriesId.toString())),
            method = MethodHTTP.POST,
            body= body,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteStateFromSeries(seriesId: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.REMOVE_SERIE_STATE, listOf(seriesId.toString())),
            method = MethodHTTP.DELETE,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteSeriesFromList(seriesId: Int, listId: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.DELETE_SERIE_FROM_LIST, listOf(seriesId.toString(),listId.toString())),
            method = MethodHTTP.DELETE,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun addWatchedEpisode(
        seriesId: Int, seasonNr: Int, epNumber: Int, token: String) {
        val body = FormBody.Builder()
            .add("season_number", seasonNr.toString())
            .add("episode_number", epNumber.toString())
            .build()
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.ADD_WATCHED_EP, listOf(seriesId.toString())),
            method = MethodHTTP.POST,
            body = body,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteSeriesList(listId: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.DELETE_LIST, listOf(listId.toString())),
            method = MethodHTTP.DELETE,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteWatchedEpisode(
        seriesId: Int, seasonNr: Int, epNumber: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.REMOVE_WATCHED_EP,
                    listOf(seriesId.toString(), seasonNr.toString(), epNumber.toString())),
            method = MethodHTTP.DELETE,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun getAllSeriesByState(state: String, token: String): List<SeriesData> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.GET_SERIES_BY_STATE, listOf(state)),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listSeriesDataObj = httpClient.send(request){ response ->
            println("Test inside response handler")
            println(response.body)
            handleResponse<ListSeriesData>(response, ListSeriesData::class.java)
        }
        return listSeriesDataObj.results
    }

    override suspend fun getAllSeriesLists(token: String): List<ContentList> {
        val request = buildRequest(
            url = cinescopeURL.joinPath(Series.GET_SERIES_LISTS),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listContentListObj = httpClient.send(request){ response ->
            handleResponse<ListOfContentList>(response, ListOfContentList::class.java)
        }
        return listContentListObj.results
    }

    override suspend fun getSeriesList(listId: Int, token: String): List<SeriesData> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.GET_LIST, listOf(listId.toString())),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listSeriesDataObj = httpClient.send(request){ response ->
            handleResponse<ListSeriesData>(response, ListSeriesData::class.java)
        }
        return listSeriesDataObj.results
    }

    override suspend fun createSeriesList(name: String, token: String): ListId {
        val body = FormBody.Builder()
            .add("name", name)
            .build()
        val request = buildRequest(
            url = cinescopeURL.joinPath(Series.CREATE_LIST),
            method = MethodHTTP.POST,
            body = body,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, ListId::class.java)
        }
    }

    override suspend fun getSeriesUserData(seriesId: Int, token: String): List<UserDataContent> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.SERIE_USER_DATA, listOf(seriesId.toString())),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listOfUserDataContentObj = httpClient.send(request){ response ->
            handleResponse<ListOfUserDataContent>(response, ListOfUserDataContent::class.java)
        }
        return listOfUserDataContentObj.results
    }

    override suspend fun getAllWatchedEpFromSeries(seriesId: Int, token: String): List<EpisodeData> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Series.GET_WATCHED_EP_LIST, listOf(seriesId.toString())),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listEpisodeDataObj = httpClient.send(request){ response ->
            handleResponse<ListEpisodeData>(response, ListEpisodeData::class.java)
        }
        return listEpisodeDataObj.results
    }
}