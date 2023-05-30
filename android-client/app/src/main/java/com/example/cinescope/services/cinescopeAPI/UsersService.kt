package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.dtos.ContentAPIDto
import com.example.cinescope.services.dtos.UserInfo
import com.example.cinescope.services.dtos.UserToken
import com.example.cinescope.services.serviceInterfaces.CinescopeUsersService
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import java.net.URL

class UsersService(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeUsersService, CinescopeService(gson, httpClient) {

    override suspend fun createUser(email: String, pwd: String): UserToken {
        val body = FormBody.Builder()
            .add("email", email)
            .add("password", pwd)
            .build()

        val request = buildRequest(
            url = cinescopeURL.joinPath(Users.CREATE_USER),
            method = MethodHTTP.POST,
            body = body
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, UserToken::class.java)
        }
    }

    override suspend fun login(email: String, pwd: String): UserToken {
        val body = FormBody.Builder()
            .add("email", email)
            .add("password", pwd)
            .build()

        val request = buildRequest(
            url = cinescopeURL.joinPath(Users.LOGIN),
            method = MethodHTTP.POST,
            body = body
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, UserToken::class.java)
        }
    }

    override suspend fun getUserInfo(token: String): UserInfo {
        val request = buildRequest(
            url = cinescopeURL.joinPath(Users.GET_USER_INFO)
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, UserInfo::class.java)
        }
    }

    override suspend fun deleteUser(userId: Int) {
        TODO("Not yet implemented")
    }
}