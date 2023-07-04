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
import com.example.cinescope.lists.listDetails.ListDetailsActivity
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
                ListsScreen(
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
                    onGetListDetails = { listId, type ->
                        ListDetailsActivity.navigate(this, listId, type)
                    },
                    onSearchRequest = { SearchActivity.navigate(this)}
                )
            } else {
                NotLoggedInScreen(
                    navController = dependencies.navController
                )
            }
        }
    }
}