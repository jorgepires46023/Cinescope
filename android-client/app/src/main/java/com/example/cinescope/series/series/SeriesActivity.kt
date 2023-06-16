package com.example.cinescope.series.series

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.utils.viewModelInit

class SeriesActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    companion object{
        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, SeriesActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private val viewModel: SeriesViewModel by viewModels {
        viewModelInit {
            SeriesViewModel(dependencies.seriesServices)
        }
    }

    override fun onResume() {
        super.onResume()

        val fakeToken = "someToken"//TODO Add user info to get credentials to make this request

        //viewModel.getPTWSeries(fakeToken)
        //viewModel.getWatchingSeries(fakeToken)
        //viewModel.getWatchedSeries(fakeToken)

        setContent{
            SeriesScreen(
                state = SeriesScreenState(
                    ptwSeries = viewModel.ptwList,
                    watchingSeries = viewModel.watchingList,
                    watchedSeries = viewModel.watchedList,
                    error = viewModel.error,
                    loading = viewModel.loading
                ),
                navController = dependencies.navController,
                onError = { viewModel.clearError() }
            )
        }
    }

}