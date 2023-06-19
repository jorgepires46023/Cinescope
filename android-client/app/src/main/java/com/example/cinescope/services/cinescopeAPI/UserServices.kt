package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.user.User
import com.example.cinescope.domain.user.UserInput
import com.example.cinescope.domain.user.UserCredentials
import com.example.cinescope.domain.user.UserInfo
import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.serviceInterfaces.CinescopeUsersServices
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.net.URL

class UserServices(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeUsersServices, CinescopeServices(gson, httpClient) {

    override suspend fun createUser(name: String, email: String, pwd: String): User {
        val userInput = UserInput(name = name, email = email, password = pwd)

        val request = buildRequest(
            url = cinescopeURL.joinPath(Users.CREATE_USER),
            method = MethodHTTP.POST,
            body = userInput.toJsonBody()
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            val cookie = handleCookie(response)
            val userInfo = handleResponse<UserInfo>(response, UserInfo::class.java)
            User(cookie, userInfo.email, userInfo.name )
        }
    }

    override suspend fun login(email: String, pwd: String): User {
        val user = UserCredentials(email = email, password = pwd)

        val request = buildRequest(
            url = cinescopeURL.joinPath(Users.LOGIN),
            method = MethodHTTP.POST,
            body = user.toJsonBody()
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request) {response ->
            val cookie = handleCookie(response)
            val userInfo = handleResponse<UserInfo>(response, UserInfo::class.java)
            User(cookie, userInfo.email, userInfo.name )
        }
        /*return httpClient.send(request){ response ->
            handleResponse(response, Token::class.java)
        }*/
    }

    override suspend fun getUserInfo(token: String): UserInfo {
        //TODO to be decided how we will handle this request: token vs id
        val request = buildRequest(
            url = cinescopeURL.joinPath(Users.GET_USER_INFO),
            token = token
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