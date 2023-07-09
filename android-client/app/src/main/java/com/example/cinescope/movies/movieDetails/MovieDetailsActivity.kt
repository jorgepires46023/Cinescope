package com.example.cinescope.movies.movieDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cookie = dependencies.userRepo.cookie
        if(cookie != null)
            viewModel.getMovieUserData(movieId, cookie)
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
                        if(cookie != null) viewModel.addMovieToList(listId, movieId, cookie)
                    },
                    onDeleteFromList = { listId ->
                        if(cookie != null) viewModel.deleteMovieFromList(listId, movieId, cookie)
                    },
                    onGetLists = {
                        if(cookie != null) viewModel.getLists(cookie)
                    }
                ),
                navController = dependencies.navController,
                loggedIn = cookie != null,
                onChangeState = {state ->
                    if(cookie != null) viewModel.changeState(state, movieId, cookie)
                },
                onSearchRequested = { SearchActivity.navigate(this)},
                onUpdate = { /*if(user != null) viewModel.getMovieUserData(movieId, user.cookie)*/ }
            )
        }
    }
}