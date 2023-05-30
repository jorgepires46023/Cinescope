package com.example.cinescope

import android.app.Application
import com.example.cinescope.services.cinescopeAPI.CinescopeService
import com.example.cinescope.services.cinescopeAPI.MoviesService
import com.example.cinescope.services.cinescopeAPI.SearchService
import com.example.cinescope.services.cinescopeAPI.SeriesService
import com.example.cinescope.services.cinescopeAPI.UsersService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.net.URL

const val TAG = "CinescopeApp"
private const val CINESCOPE_DOMAIN = "http://localhost:9000"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    //val userInfoRepo: UserInfoRepository
    val httpClient: OkHttpClient
        get() = OkHttpClient()
    val gson: Gson
        get() = Gson()
    val cinescopeService: CinescopeService
}
private val cinescopeApiHome = URL(CINESCOPE_DOMAIN)
/**
 * The application class to be used as a Service Locator.
 */
class TicTacToeApplication : DependenciesContainer, Application() {

    override val cinescopeService: CinescopeService by lazy {
        SearchService(cinescopeApiHome, gson, httpClient)
        UsersService(cinescopeApiHome, gson, httpClient)
        MoviesService(cinescopeApiHome, gson, httpClient)
        SeriesService(cinescopeApiHome, gson, httpClient)
    }
}