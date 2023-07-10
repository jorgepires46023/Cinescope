package com.example.cinescope.services

import com.example.cinescope.services.cinescopeAPI.UserServices
import com.example.cinescope.services.mockdata.cookieRuiBorders
import com.example.cinescope.services.mockdata.createUserResponse
import com.example.cinescope.services.mockdata.emailRuiBorders
import com.example.cinescope.services.mockdata.expectedCreateUserResponse
import com.example.cinescope.services.mockdata.expectedGetUserInfoResponse
import com.example.cinescope.services.mockdata.expectedLoginResponse
import com.example.cinescope.services.mockdata.getUserInfoResponse
import com.example.cinescope.services.mockdata.loginResponse
import com.example.cinescope.services.mockdata.nameRuiBorders
import com.example.cinescope.services.mockdata.pwdRuiBorders
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

class UserInputServicesTests {
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
            val actual = userServices.createUser(nameRuiBorders, emailRuiBorders, pwdRuiBorders)

            // Assert
            Assert.assertEquals(expectedCreateUserResponse, actual)
        }

    //LOGIN TESTS
    @Test
    fun `login returns user token when the request is successful`() {
            runBlocking {
                // Arrange
                val mockServer = testRule.server
                mockServer.enqueue(
                    response = MockResponse()
                        .setHeader("content-type", jsonMediaType)
                        .setBody(jsonFormatter.toJson(loginResponse))
                )

                val userServices = UserServices(mockServer.url("/").toUrl(), gson, httpClient)

                // Act
                val actual = userServices.login(emailRuiBorders, pwdRuiBorders)

                // Assert
                Assert.assertEquals(expectedLoginResponse, actual)
            }
        }

    //GET USER INFO TESTS
    @Test
    fun `getUserInfo returns name and email when the request is successful`(): Unit =
        runBlocking {
            // Arrange
            val mockServer = testRule.server
            mockServer.enqueue(response = MockResponse()
                .setHeader("content-type", jsonMediaType)
                .setBody(jsonFormatter.toJson( getUserInfoResponse))
            )

            val userServices = UserServices(mockServer.url("/").toUrl(), gson, httpClient)

            if(cookieRuiBorders != null) {
                // Act
                val actual = userServices.getUserInfo(cookieRuiBorders)

                // Assert
                Assert.assertEquals(expectedGetUserInfoResponse, actual)
            }
        }
}