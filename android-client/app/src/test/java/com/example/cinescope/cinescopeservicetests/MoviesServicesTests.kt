package com.example.cinescope.cinescopeservicetests

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

class MoviesServicesTests {
    @get:Rule
    val testRule = MockWebServerRule()

    val httpClient: OkHttpClient = OkHttpClient()
    val gson: Gson = Gson()

    private val jsonFormatter: Gson = GsonBuilder().create()

    private val JsonMediaType = ("application/json").toMediaType()

    //ADD MOVIE TO LIST TESTS

    //CHANGE MOVIE STATE TESTS

    //DELETE STATE FROM MOVIE TESTS

    //DELETE MOVIE FROM LIST TESTS

    //DELETE MOVIES LIST TESTS

    //GET ALL MOVIES BY STATE TESTS

    //GET ALL MOVIES LISTS TESTS

    //GET MOVIES LIST TESTS

    //CREATE MOVIES LIST TESTS

    //GET MOVIE USER DATA TESTS

}