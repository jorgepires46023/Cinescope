package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.dtos.MovieUserData
import com.example.cinescope.services.dtos.MovieListId
import com.example.cinescope.services.dtos.MovieState
import com.example.cinescope.services.dtos.MovieUserDataDto
import com.example.cinescope.services.dtos.UserToken
import com.example.cinescope.services.serviceInterfaces.CinescopeMoviesService
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
) : CinescopeMoviesService, CinescopeServices(gson, httpClient) {
    override suspend fun addMovieToList(movieId: Int, listId: Int, token: String) {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Movies.ADD_MOVIE, listOf(movieId.toString(),listId.toString())),
            method = MethodHTTP.POST,
            token = token
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, UserToken::class.java)
        }
    }

    override suspend fun createMoviesList(listName: String, token: String) {
        val body = FormBody.Builder()
            .add("name", listName)
            .build()
        val request = buildRequest(cinescopeURL.joinPath(Movies.CREATE_LIST),
            method = MethodHTTP.POST,
            body = body,
            token = token
        )

        httpClient.send(request){ response ->
            handleResponse<MovieListId>(response, MovieListId::class.java)
        }
    }

    override suspend fun changeMovieState(movieId: Int, state: String, token: String) {
        val body = FormBody.Builder()
            .add("state", state)
            .build()
        val request = buildRequest(
            url = cinescopeURL.joinPathWithVariables(Movies.CHANGE_STATE, listOf(movieId.toString())),
            method = MethodHTTP.POST,
            body = body,
            token =  token
        )
        httpClient.send(request){response ->
            handleResponse<MovieState>(response, MovieState::class.java)
        }
    }

    override suspend fun getListsByState(state: String, token: String): List<MovieUserData> {
        val request = buildRequest(
            url = cinescopeURL.joinPathWithVariables(Movies.GET_LIST_BY_STATE,
                listOf(state)),
            token =  token
        )
        val moviesDataDto = httpClient.send(request){response ->
            handleResponse<List<MovieUserDataDto>>(response, MovieUserDataDto::class.java)
        }
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesList(movieId: Int, listId: Int, token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMoviesList(movieId: Int, listId: Int, token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovieFromList(movieId: Int, listId: Int, token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStateFromMovie(movieId: Int, listId: Int, token: String) {
        TODO("Not yet implemented")
    }
}