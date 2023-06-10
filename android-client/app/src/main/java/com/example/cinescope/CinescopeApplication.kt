package com.example.cinescope

import android.app.Application
import com.example.cinescope.services.cinescopeAPI.CinescopeServices
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import com.example.cinescope.services.cinescopeAPI.SearchServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import com.example.cinescope.services.cinescopeAPI.UserServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.net.URL

const val TAG = "CinescopeApp"
private const val CINESCOPE_DOMAIN = myAdress
    //"http://10.0.2.2:9000" -> Adress to access API in emulator device
    //"http://localhost:9000"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    //val userInfoRepo: UserInfoRepository
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
    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cache(Cache(directory = cacheDir, maxSize = 50 * 1024 * 1024))
            .build()
    }
    private val gson: Gson by lazy {
        GsonBuilder().create()
    }
    //TODO verificar by lazy
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

