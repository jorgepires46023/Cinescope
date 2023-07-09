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