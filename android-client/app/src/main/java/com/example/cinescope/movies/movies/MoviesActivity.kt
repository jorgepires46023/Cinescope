package com.example.cinescope.movies.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.domain.MovieState
import com.example.cinescope.movies.movieDetails.MovieDetailsActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.ui.NotLoggedInScreen
import com.example.cinescope.utils.viewModelInit

class MoviesActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: MoviesViewModel by viewModels {
        viewModelInit {
            MoviesViewModel(dependencies.moviesServices)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = dependencies.userRepo.user
        if(user != null){
            viewModel.getMoviesByState(MovieState.PTW.state, user.cookie) //Default         //Everytime the activity is on, a request will be made to get all possible states
            viewModel.getMoviesByState(MovieState.WATCHED.state, user.cookie)               //This allows for, whatever the tab selected, after performing a back, it will be updated
        }

        setContent{
            if(user != null){
                MoviesScreen(
                    state = MoviesScreenState(
                        ptwMovies = viewModel.ptwList,
                        watchedMovies = viewModel.watchedList,
                        error = viewModel.error,
                        loading = viewModel.loading
                    ),
                    navController = dependencies.navController,
                    onError = { viewModel.clearError() },
                    onTabChanged = {state -> viewModel.getMoviesByState(state, user.cookie) },
                    onGetDetails = { moviesId ->
                        MovieDetailsActivity.navigate(this, moviesId)
                    },
                    onSearchRequested = { SearchActivity.navigate(this)}
                )
            }else{
                NotLoggedInScreen(navController = dependencies.navController)
            }
        }
    }
}