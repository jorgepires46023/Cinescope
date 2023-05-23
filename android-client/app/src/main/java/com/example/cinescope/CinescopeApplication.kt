package com.example.cinescope

import android.app.Application
import com.example.cinescope.service.cinescopeAPI.CinescopeService
import com.example.cinescope.service.cinescopeAPI.SearchService
import java.net.URL

const val TAG = "CinescopeApp"
private const val CINESCOPE_DOMAIN = "http://localhost:9000"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    //val userInfoRepo: UserInfoRepository
    val cinescopeService: CinescopeService
}
private val cinescopeApiHome = URL(CINESCOPE_DOMAIN)
/**
 * The application class to be used as a Service Locator.
 */
class TicTacToeApplication : DependenciesContainer, Application() {
    /*
    override val cinescopeSearchService: SearchService by lazy {
        SearchService(cinescopeURL = cinescopeApiHome)
    }
    */
    override val cinescopeService: CinescopeService by lazy {
        SearchService(cinescopeURL = cinescopeApiHome)
    }



}