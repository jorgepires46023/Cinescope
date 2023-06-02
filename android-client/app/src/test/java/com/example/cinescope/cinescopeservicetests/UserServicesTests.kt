package com.example.cinescope.cinescopeservicetests

import com.example.cinescope.cinescopeservicetests.mockdata.createUserResponse
import com.example.cinescope.cinescopeservicetests.mockdata.emailRuiBorders
import com.example.cinescope.cinescopeservicetests.mockdata.expectedCreateUserResponse
import com.example.cinescope.cinescopeservicetests.mockdata.expectedGetUserInfoResponse
import com.example.cinescope.cinescopeservicetests.mockdata.expectedLoginResponse
import com.example.cinescope.cinescopeservicetests.mockdata.getUserInfoResponse
import com.example.cinescope.cinescopeservicetests.mockdata.loginResponse
import com.example.cinescope.cinescopeservicetests.mockdata.pwdRuiBorders
import com.example.cinescope.cinescopeservicetests.mockdata.tokenRuiBorders
import com.example.cinescope.services.cinescopeAPI.UserServices
import com.example.cinescope.testutils.MockWebServerRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class UserServicesTests {
    @get:Rule
    val testRule = MockWebServerRule()

    private val httpClient: OkHttpClient = OkHttpClient()
    private val gson: Gson = Gson()

    private val jsonFormatter: Gson = GsonBuilder().create()

    private val jsonMediaType = ("application/json").toMediaType()

    //CREATE USER TESTS
    @Test
    fun `createUser returns user token when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( createUserResponse))
            )

            val userServices = UserServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = userServices.createUser(emailRuiBorders, pwdRuiBorders)

            // Assert
            Assert.assertEquals(expectedCreateUserResponse, actual)
        }

    //LOGIN TESTS
    @Test
    fun `login returns user token when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( loginResponse))
            )

            val userServices = UserServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = userServices.login(emailRuiBorders, pwdRuiBorders)

            // Assert
            Assert.assertEquals(expectedLoginResponse, actual)
        }

    //GET USER INFO TESTS(TODO - not fully implemented yet)
    @Test
    fun `getUserInfo returns email and password when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( getUserInfoResponse))
            )

            val userServices = UserServices(mockServer.url("/").toUrl(), gson, httpClient)

            // Act
            val actual = userServices.getUserInfo(tokenRuiBorders)

            // Assert
            Assert.assertEquals(expectedGetUserInfoResponse, actual)
        }
}