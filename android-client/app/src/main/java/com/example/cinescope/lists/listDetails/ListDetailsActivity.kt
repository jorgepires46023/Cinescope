package com.example.cinescope.lists.listDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.movies.movieDetails.MovieDetailsActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.series.seriesDetails.SeriesDetailsActivity
import com.example.cinescope.utils.viewModelInit

class ListDetailsActivity: ComponentActivity() {

    private val dependencies by lazy { application as DependenciesContainer }

    companion object {

        private const val LIST_ID = "LIST_ID"
        private const val TYPE = "TYPE"

         fun navigate(context: Context, listId: Int, type: String) {
             with(context) {
                 val intent = Intent(this, ListDetailsActivity::class.java)
                 intent.putExtra(LIST_ID, listId.toString())
                 intent.putExtra(TYPE, type)
                 startActivity(intent)
             }
         }
    }

    private val listId: Int by lazy {
        val listId = checkNotNull(intent.getStringExtra(LIST_ID))
        listId.toInt()
    }

    private val type: String by lazy {
        checkNotNull(intent.getStringExtra(TYPE))
    }

    private val viewModel: ListDetailsViewModel by viewModels {
        viewModelInit {
            ListDetailsViewModel(dependencies.moviesServices, dependencies.seriesServices)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cookie = dependencies.userRepo.cookie
        if (cookie != null) {
            viewModel.getListDetails(listId, type, cookie)
        }

        setContent {
            if (type == "Movies") {
                MoviesListDetailsScreen(
                    list = viewModel.moviesList,
                    onGetDetails = {id -> MovieDetailsActivity.navigate(this, id)},
                    onDeleteFromList = {id ->
                        if (cookie != null) {
                            viewModel.deleteContentFromList(listId, id, type, cookie)
                            viewModel.getListDetails(listId, type, cookie)
                        }
                    },
                    onSearchRequest = { SearchActivity.navigate(this) },
                    navController = dependencies.navController
                )
            } else {
                SeriesListDetailsScreen(
                    list = viewModel.seriesList,
                    onGetDetails = { id -> SeriesDetailsActivity.navigate(this, id) },
                    onDeleteFromList = { id ->
                        if (cookie != null) {
                            viewModel.deleteContentFromList(listId, id, type, cookie)
                            viewModel.getListDetails(listId, type, cookie)
                        }
                    },
                    onSearchRequest = { SearchActivity.navigate(this) },
                    navController = dependencies.navController
                )
            }
        }
    }
}