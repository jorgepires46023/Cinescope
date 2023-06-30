package com.example.cinescope.movies.movieDetails

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.search.SearchActivity
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
            MovieDetailsViewModel(dependencies.searchServices, dependencies.moviesServices)
        }
    }

    private val movieId: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(MOVIE_ID))
        id.toInt()
    }

    override fun onResume() {
        super.onResume()
        val user = dependencies.userRepo.user
        if(user != null)
            viewModel.getMovieUserData(movieId, user.cookie)
        viewModel.getMovieDetails(movieId)

        setContent{
            MovieDetailsScreen(
                state = MovieDetailsState(
                    movie = viewModel.movie,
                    loading = viewModel.loading,
                    error = viewModel.error,

                ),
                userData = MovieUserData(
                    movieData = viewModel.userData,
                    lists = viewModel.lists,
                    onAddToList = { listId ->
                        if(user != null) viewModel.addMovieToList(listId, movieId, user.cookie)
                    },
                    onDeleteFromList = { listId ->
                        if(user != null) viewModel.deleteMovieFromList(listId, movieId, user.cookie)
                    },
                    onGetLists = {
                        if(user != null) viewModel.getLists(user.cookie)
                    }
                ),
                navController = dependencies.navController,
                loggedIn = user != null,
                onChangeState = {state ->
                    if(user != null) viewModel.changeState(state, movieId, user.cookie)
                },
                onSearchRequested = { SearchActivity.navigate(this)},
                onUpdate = { /*if(user != null) viewModel.getMovieUserData(movieId, user.cookie)*/ }
            )
        }
    }
}