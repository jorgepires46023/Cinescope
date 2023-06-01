package com.example.cinescope

import android.app.Application
import com.example.cinescope.services.cinescopeAPI.CinescopeServices
import com.example.cinescope.services.cinescopeAPI.MoviesServices
import com.example.cinescope.services.cinescopeAPI.SearchServices
import com.example.cinescope.services.cinescopeAPI.SeriesServices
import com.example.cinescope.services.cinescopeAPI.UserServices
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
    val cinescopeServices: CinescopeServices
}
private val cinescopeApiHome = URL(CINESCOPE_DOMAIN)
/**
 * The application class to be used as a Service Locator.
 */
class CinescopeApplication : DependenciesContainer, Application() {

    override val cinescopeServices: CinescopeServices by lazy {
        SearchServices(cinescopeApiHome, gson, httpClient)
        UserServices(cinescopeApiHome, gson, httpClient)
        MoviesServices(cinescopeApiHome, gson, httpClient)
        SeriesServices(cinescopeApiHome, gson, httpClient)
    }
}