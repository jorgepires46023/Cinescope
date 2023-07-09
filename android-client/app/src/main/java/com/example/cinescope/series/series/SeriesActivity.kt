package com.example.cinescope.series.series

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.domain.SeriesState
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.series.seriesDetails.SeriesDetailsActivity
import com.example.cinescope.ui.NotLoggedInScreen
import com.example.cinescope.utils.viewModelInit

class SeriesActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: SeriesViewModel by viewModels {
        viewModelInit {
            SeriesViewModel(dependencies.seriesServices)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = dependencies.userRepo.user
        if(user != null){
            viewModel.getSeriesByState(SeriesState.PTW.state, user.cookie) //Default
            viewModel.getSeriesByState(SeriesState.WATCHING.state, user.cookie)        //Everytime the activity is on, a request will be made to get all possible states
            viewModel.getSeriesByState(SeriesState.WATCHED.state, user.cookie)         //This allows for, whatever the tab selected, after performing a back, it will be updated
        }

        //TODO forcar update quando back e realizado

        setContent{
            if(user != null){
                SeriesScreen(
                    state = SeriesScreenState(
                        ptwSeries = viewModel.ptwList,
                        watchingSeries = viewModel.watchingList,
                        watchedSeries = viewModel.watchedList,
                        error = viewModel.error,
                        loading = viewModel.loading
                    ),
                    navController = dependencies.navController,
                    onError = { viewModel.clearError() },
                    onTabChanged = {state -> viewModel.getSeriesByState(state, user.cookie) },
                    onGetDetails = { seriesId ->
                        SeriesDetailsActivity.navigate(this, seriesId)
                    },
                    onSearchRequested = { SearchActivity.navigate(this)}
                )
            }else{
                NotLoggedInScreen(navController = dependencies.navController)
            }
        }
    }

}