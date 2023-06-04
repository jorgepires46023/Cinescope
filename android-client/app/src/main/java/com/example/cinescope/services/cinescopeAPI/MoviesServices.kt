package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.dtosMapping.ListMovieData
import com.example.cinescope.services.dtosMapping.ListOfContentList
import com.example.cinescope.services.dtosMapping.ListOfUserDataContent
import com.example.cinescope.services.serviceInterfaces.CinescopeMoviesServices
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import java.net.URL

class MoviesServices(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeMoviesServices, CinescopeServices(gson, httpClient) {
    override suspend fun addMovieToList(movieId: Int, listId: Int, token: String) {
        val body = FormBody.Builder().build()
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.ADD_MOVIE, listOf(movieId.toString(),listId.toString())),
            method = MethodHTTP.POST,
            body = body,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun changeMovieState(movieId: Int, state: String, token: String) {
        val body = FormBody.Builder()
            .add("state", state)
            .build()
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.CHANGE_STATE, listOf(movieId.toString())),
            method = MethodHTTP.POST,
            body = body,
            token = token
        )
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteStateFromMovie(movieId: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.REMOVE_MOVIE_STATE, listOf(movieId.toString())),
            method = MethodHTTP.DELETE,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteMovieFromList(movieId: Int, listId: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.DELETE_MOVIE_FROM_LIST, listOf(listId.toString(),movieId.toString())),
            method = MethodHTTP.DELETE,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun deleteMoviesList(listId: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.DELETE_LIST, listOf(listId.toString())),
            method = MethodHTTP.DELETE,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        httpClient.send(request){ response ->
            handleEmptyResponse(response)
        }
    }

    override suspend fun getAllMoviesByState(state: String, token: String): List<MovieData> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.GET_LIST_BY_STATE, listOf(state)),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listMovieDataObj = httpClient.send(request){ response ->
            handleResponse<ListMovieData>(response, ListMovieData::class.java)
        }

        return listMovieDataObj.results
    }

    override suspend fun getAllMoviesLists(token: String): List<ContentList> {
        val request = buildRequest(
            url = cinescopeURL.joinPath(Movies.GET_MOVIES_LISTS),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listOfContentListObj = httpClient.send(request){ response ->
            handleResponse<ListOfContentList>(response, ListOfContentList::class.java)
        }
        return listOfContentListObj.results
    }

    override suspend fun getMoviesList(listId: Int, token: String): List<MovieData> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.GET_LIST, listOf(listId.toString())),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listMovieDataObj = httpClient.send(request){ response ->
            handleResponse<ListMovieData>(response, ListMovieData::class.java)
        }

        return listMovieDataObj.results
    }

    override suspend fun createMoviesList(name: String, token: String): ListId {
        val body = FormBody.Builder()
            .add("name", name)
            .build()
        val request = buildRequest(
            url = cinescopeURL.joinPath(Movies.CREATE_LIST),
            method = MethodHTTP.POST,
            body = body,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, ListId::class.java)
        }
    }

    override suspend fun getMovieUserData(movieId: Int, token: String): List<UserDataContent> {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.MOVIE_USER_DATA, listOf(movieId.toString())),
            method = MethodHTTP.GET,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        val listOfUserDataContent = httpClient.send(request){ response ->
            handleResponse<ListOfUserDataContent>(response, ListOfUserDataContent::class.java)
        }
        return listOfUserDataContent.results
    }

}