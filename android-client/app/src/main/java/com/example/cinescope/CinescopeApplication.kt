package com.example.cinescope

import android.app.Application
import com.example.cinescope.domain.user.UserInfoRepository
import com.example.cinescope.domain.user.UserRepositorySharedPrefs
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import com.example.cinescope.services.cinescopeAPI.SearchServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import com.example.cinescope.services.cinescopeAPI.UserServices
import com.example.cinescope.ui.bottombar.NavController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.net.URL

private const val CINESCOPE_DOMAIN = "https://311e-2001-8a0-f95b-a300-71fb-963b-c350-e105.ngrok-free.app"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    val navController: NavController
    val userRepo: UserInfoRepository
    //Cinescope Services
    val searchServices: SearchServices
    val userServices: UserServices
    val moviesServices: MoviesServices
    val seriesServices: SeriesServices
}

private val cinescopeApiHome = URL(CINESCOPE_DOMAIN)

/**
 * The application class to be used as a Service Locator.
 */
class CinescopeApplication : DependenciesContainer, Application() {
    override val navController: NavController by lazy {
        NavController(this)
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .build()
    }

    private val gson: Gson by lazy {
        GsonBuilder().create()
    }
    override val userRepo: UserInfoRepository
        get() = UserRepositorySharedPrefs(this, gson)

    override val searchServices: SearchServices by lazy {
        SearchServices(cinescopeApiHome, gson, httpClient)
    }
    override val userServices: UserServices by lazy {
        UserServices(cinescopeApiHome, gson, httpClient)
    }

    override val moviesServices: MoviesServices by lazy {
        MoviesServices(cinescopeApiHome, gson, httpClient)
    }
    override val seriesServices: SeriesServices by lazy {
        SeriesServices(cinescopeApiHome, gson, httpClient)
    }
}

