package com.example.cinescope.series.seriesDetails

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.utils.viewModelInit

class SeriesDetailsActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }
    companion object{
        private const val SERIES_ID = "SERIES_ID"
        fun navigate(origin: Context, seriesId: Int){
            with(origin){
                val intent = Intent(this, SeriesDetailsActivity::class.java)
                intent.putExtra(SERIES_ID, seriesId.toString())
                startActivity(intent)
            }
        }
    }
    private val viewModel: SeriesDetailsScreenViewModel by viewModels {
        viewModelInit{
            SeriesDetailsScreenViewModel(dependencies.searchServices)
        }
    }

    private val seriesId: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(SERIES_ID))
        id.toInt()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getSeriesDetails(seriesId)

        setContent{
            SeriesDetailsScreen(
                state = SeriesDetailsState(
                    series = viewModel.series,
                    loading = viewModel.loading,
                    error = viewModel.error
                ),
                navController = dependencies.navController
            )
        }
    }
}