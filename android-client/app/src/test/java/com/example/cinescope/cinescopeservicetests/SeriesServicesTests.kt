package com.example.cinescope.cinescopeservicetests

import com.example.cinescope.testutils.MockWebServerRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.junit.Rule

class SeriesServicesTests {
    @get:Rule
    val testRule = MockWebServerRule()

    val httpClient: OkHttpClient = OkHttpClient()
    val gson: Gson = Gson()

    private val jsonFormatter: Gson = GsonBuilder().create()

    private val JsonMediaType = ("application/json").toMediaType()

    //ADD SERIES TO LIST TESTS

    //CHANGE SERIES STATE TESTS

    //DELETE STATE FROM SERIES TESTS

    //DELETE SERIES FROM LIST TESTS

    //ADD WATCHED EPISODE TESTS

    //DELETE SERIES LIST TESTS

    //DELETE WATCHED EPISODE TESTS

    //GET ALL SERIES BY STATE TESTS

    //GET ALL SERIES LISTS TESTS

    //GET SERIES LIST TESTS

    //CREATE SERIES LIST TESTS

    //GET SERIES USER DATA TESTS

    //GET ALL WATCHED EPISODES FROM SERIES TESTS

}