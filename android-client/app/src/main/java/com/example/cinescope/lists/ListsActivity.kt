package com.example.cinescope.lists

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.domain.user.User
import com.example.cinescope.lists.ui.ListsState
import com.example.cinescope.movies.movieDetails.MovieDetailsActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.series.seriesDetails.SeriesDetailsActivity
import com.example.cinescope.ui.NotLoggedInScreen
import com.example.cinescope.utils.viewModelInit

class ListsActivity: ComponentActivity() {

    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: ListsViewModel by viewModels {
        viewModelInit{
            ListsViewModel(dependencies.moviesServices, dependencies.seriesServices)
        }
    }
    companion object {
        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, ListsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val user = dependencies.userRepo.user
        if(user != null){
            viewModel.getMoviesLists(user.cookie)
            viewModel.getSeriesLists(user.cookie)
        }

        setContent{
            if(user != null){
                GetListScreen(user = user)
            //This composable will define which screen, from multiple ones, should be displayed
            } else {
                NotLoggedInScreen(
                    navController = dependencies.navController
                )
            }
        }
    }
    @Composable
    fun GetListScreen(user: User){
        var state by remember{ mutableIntStateOf(ListsState.Lists) }
        when(state){
            0 -> ListsScreen(
                navController = dependencies.navController,
                movieActions = MovieActions(
                    onCreateMovieList = { name ->
                        viewModel.createMovieList(name, user.cookie)
                    },
                    moviesLists = viewModel.moviesLists,
                    onUpdateMoviesLists = { viewModel.getMoviesLists(user.cookie) },
                    deleteMovieList = { listId ->
                        viewModel.deleteMovieList(listId, user.cookie)
                        viewModel.getMoviesList(listId, user.cookie)
                    }
                ),
                seriesActions = SeriesActions(
                    onCreateSeriesList = { name -> viewModel.createSeriesList(name, user.cookie) },
                    seriesLists = viewModel.seriesLists,
                    onUpdateSeriesLists = { viewModel.getSeriesLists(user.cookie) },
                    deleteSeriesList = { listId ->
                        viewModel.deleteSeriesList(listId, user.cookie)
                    }
                ),
                onChangeScreen = { newState, listId ->
                    state = newState
                    when(newState){
                        ListsState.MovieListDetails -> viewModel.getMoviesList(listId, user.cookie)
                        ListsState.SeriesListDetails -> viewModel.getSeriesList(listId, user.cookie)
                    }
                },
                onSearchRequest = { SearchActivity.navigate(this)}
            )
            1 -> MoviesListScreen(
                navController = dependencies.navController,
                moviesList = viewModel.currentMovieList,
                onGetMovieDetails = { movieId ->
                    MovieDetailsActivity.navigate(this, movieId)
                },
                onBackRequest = {
                    viewModel.clearCurrentMovieList()
                    state = ListsState.Lists
                },
                onDeleteMovieFromList = { movieId ->
                    viewModel.currentMovieList?.let {
                        viewModel.deleteMovieFromList(movieId, it.info.id, user.cookie)
                        viewModel.getMoviesList(it.info.id, user.cookie)
                    }
                },
                onSearchRequest = {SearchActivity.navigate(this)}
            )
            2 -> SeriesListScreen(
                navController = dependencies.navController,
                seriesList = viewModel.currentSeriesList,
                onGetSeriesDetails = { seriesId ->
                    SeriesDetailsActivity.navigate(this, seriesId)
                },
                onBackRequest = {
                    viewModel.clearCurrentSeriesList()
                    state = ListsState.Lists
                },
                onDeleteFromList = { seriesId ->
                    viewModel.currentSeriesList?.let {
                        viewModel.deleteSeriesFromList(seriesId, it.info.id, user.cookie)
                        viewModel.getSeriesList(it.info.id, user.cookie)
                    }
                },
                onSearchRequest = {SearchActivity.navigate(this)}
            )
            else -> Toast.makeText(this, "Error displaying content", Toast.LENGTH_SHORT).show()
        }
    }
}