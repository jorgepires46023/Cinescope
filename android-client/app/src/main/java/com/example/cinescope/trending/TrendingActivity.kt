package com.example.cinescope.trending

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.movies.movieDetails.MovieDetailsActivity
import com.example.cinescope.series.seriesDetails.SeriesDetailsActivity
import com.example.cinescope.utils.viewModelInit

const val TRENDING_ACTIVITY_TAG = "Trending Activity"
class TrendingActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer}


    private val viewModel: TrendingScreenViewModel by viewModels {
        viewModelInit{
            TrendingScreenViewModel(dependencies.searchServices)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                onGetMovieDetails = { movieId ->
                    MovieDetailsActivity.navigate(this, movieId = movieId)
                },
                onGetSeriesDetails = {seriesId ->
                    SeriesDetailsActivity.navigate(this, seriesId = seriesId)
                },
                onError = { viewModel.clearError() }
            )
        }
    }
}