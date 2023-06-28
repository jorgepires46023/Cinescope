package com.example.cinescope.trending

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.domain.MediaType
import com.example.cinescope.movies.movieDetails.MovieDetailsActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.series.seriesDetails.SeriesDetailsActivity
import com.example.cinescope.utils.viewModelInit

const val TRENDING_ACTIVITY_TAG = "Trending Activity"
class TrendingActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer}

    companion object{
        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, TrendingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val viewModel: TrendingScreenViewModel by viewModels {
        viewModelInit{
            TrendingScreenViewModel(dependencies.searchServices)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dependencies.navController
        viewModel.getPopularMovies()
        viewModel.getPopularSeries()

        setContent {
            TrendingScreen(
                state = TrendingScreenState(
                    popMovies = viewModel.popularMovies,
                    popSeries = viewModel.popularSeries,
                    loading = false,
                    error = viewModel.error
                ),
                onSearchRequested = {
                    SearchActivity.navigate(this)
                },
                onGetDetails = { id, type ->
                    if (type == MediaType.Movie) {
                        MovieDetailsActivity.navigate(this, movieId = id)
                    } else {
                        SeriesDetailsActivity.navigate(this, seriesId = id)
                    }
                },
                onError = { viewModel.clearError() },
                navController = dependencies.navController
            )
        }
    }
}