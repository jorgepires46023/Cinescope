package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.services.cinescopeAPI.outputs.CreateListOutput
import com.example.cinescope.services.cinescopeAPI.outputs.StateOutput
import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.dtosMapping.ListMovieData
import com.example.cinescope.services.dtosMapping.ListOfContentList
import com.example.cinescope.services.dtosMapping.ListOfUserDataContent
import com.example.cinescope.services.serviceInterfaces.CinescopeMoviesServices
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import java.net.URL

class MoviesServices(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeMoviesServices, CinescopeServices(gson, httpClient) {
    override suspend fun addMovieToList(movieId: Int, listId: Int, cookie: Cookie) {
        val body = RequestBody.toJsonBody()
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.ADD_MOVIE, listOf(movieId.toString(),listId.toString())),
            method = MethodHTTP.POST,
            body = body,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun changeMovieState(movieId: Int, state: String, cookie: Cookie) {
        val stateObj = StateOutput(state)
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.CHANGE_STATE, listOf(movieId.toString())),
            method = MethodHTTP.POST,
            body = stateObj.toJsonBody(),
            cookie = cookie
        )
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteStateFromMovie(movieId: Int, cookie: Cookie) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.REMOVE_MOVIE_STATE, listOf(movieId.toString())),
            method = MethodHTTP.DELETE,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteMovieFromList(movieId: Int, listId: Int, cookie: Cookie) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.DELETE_MOVIE_FROM_LIST, listOf(listId.toString(),movieId.toString())),
            method = MethodHTTP.DELETE,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteMoviesList(listId: Int, cookie: Cookie) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.DELETE_LIST, listOf(listId.toString())),
            method = MethodHTTP.DELETE,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun getAllMoviesByState(state: String, cookie: Cookie): List<MovieData> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.GET_LIST_BY_STATE, listOf(state)),
            method = MethodHTTP.GET,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listMovieDataObj = httpClient.send(request){ response ->
            handleResponse<ListMovieData>(response, ListMovieData::class.java)
        }

        return listMovieDataObj.results
    }

    override suspend fun getAllMoviesLists(cookie: Cookie): List<ContentList> {
        val request = buildRequest(
            url = cinescopeURL.joinPath(Movies.GET_MOVIES_LISTS),
            method = MethodHTTP.GET,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listOfContentListObj = httpClient.send(request){ response ->
            handleResponse<ListOfContentList>(response, ListOfContentList::class.java)
        }
        return listOfContentListObj.results
    }

    override suspend fun getMoviesList(listId: Int, cookie: Cookie): List<MovieData> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.GET_LIST, listOf(listId.toString())),
            method = MethodHTTP.GET,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listMovieDataObj = httpClient.send(request){ response ->
            handleResponse<ListMovieData>(response, ListMovieData::class.java)
        }

        return listMovieDataObj.results
    }

    override suspend fun createMoviesList(name: String, cookie: Cookie): ListId {
        val nameObj = CreateListOutput(name)
        val request = buildRequest(
            url = cinescopeURL.joinPath(Movies.CREATE_LIST),
            method = MethodHTTP.POST,
            body = nameObj.toJsonBody(),
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, ListId::class.java)
        }
    }

    override suspend fun getMovieUserData(movieId: Int, cookie: Cookie): List<UserDataContent> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.MOVIE_USER_DATA, listOf(movieId.toString())),
            method = MethodHTTP.GET,
            cookie = cookie
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listOfUserDataContent = httpClient.send(request){ response ->
            handleResponse<ListOfUserDataContent>(response, ListOfUserDataContent::class.java)
        }
        return listOfUserDataContent.results
    }

}