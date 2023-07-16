package com.example.cinescope.lists.lists

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.lists.listDetails.ListDetailsActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.ui.NotLoggedInScreen
import com.example.cinescope.utils.viewModelInit

class ListsActivity: ComponentActivity() {

    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: ListsViewModel by viewModels {
        viewModelInit{
            ListsViewModel(dependencies.moviesServices, dependencies.seriesServices)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cookie = dependencies.userRepo.cookie
        if(cookie != null){
            viewModel.getMoviesLists(cookie)
            viewModel.getSeriesLists(cookie)
        }

        setContent{
            if(cookie != null){
                ListsScreen(
                    navController = dependencies.navController,
                    movieActions = MovieActions(
                        onCreateMovieList = { name ->
                            viewModel.createMovieList(name, cookie)
                        },
                        moviesLists = viewModel.moviesLists,
                        onUpdateMoviesLists = { viewModel.getMoviesLists(cookie) },
                        deleteMovieList = { listId ->
                            viewModel.deleteMovieList(listId, cookie)
                            viewModel.getMoviesList(listId, cookie)
                        }
                    ),
                    seriesActions = SeriesActions(
                        onCreateSeriesList = { name -> viewModel.createSeriesList(name, cookie) },
                        seriesLists = viewModel.seriesLists,
                        onUpdateSeriesLists = { viewModel.getSeriesLists(cookie) },
                        deleteSeriesList = { listId ->
                            viewModel.deleteSeriesList(listId, cookie)
                        }
                    ),
                    error = viewModel.error,
                    onError = { viewModel.clearError() },
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