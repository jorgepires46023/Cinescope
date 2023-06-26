package com.example.cinescope.movies.movieDetails

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.utils.viewModelInit

class MovieDetailsActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }
    companion object{
        private const val MOVIE_ID = "MOVIE_ID"
        fun navigate(origin: Context, movieId: Int){
            with(origin){
                val intent = Intent(this, MovieDetailsActivity::class.java)
                intent.putExtra(MOVIE_ID, movieId.toString())
                startActivity(intent)
            }
        }
    }
    private val viewModel: MovieDetailsViewModel by viewModels {
        viewModelInit{
            MovieDetailsViewModel(dependencies.searchServices)
        }
    }

    private val movieId: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(MOVIE_ID))
        id.toInt()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getMovieDetails(movieId)

        setContent{
            MovieDetailsScreen(
                state = MovieDetailsState(
                    movie = viewModel.movie,
                    loading = viewModel.loading,
                    error = viewModel.error
                ),
                navController = dependencies.navController
            )
        }
    }
}