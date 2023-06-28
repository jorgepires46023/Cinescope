package com.example.cinescope.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.domain.MediaType
import com.example.cinescope.movies.movieDetails.MovieDetailsActivity
import com.example.cinescope.series.seriesDetails.SeriesDetailsActivity
import com.example.cinescope.utils.viewModelInit

class SearchActivity: ComponentActivity() {

    private val dependencies by lazy { application as DependenciesContainer }

    companion object {

        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val viewModel : SearchViewModel by viewModels {
        viewModelInit {
            SearchViewModel(dependencies.searchServices)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SearchScreen(
                onBackRequest = { finish() },
                onSearch = { query ->
                    viewModel.search(query)
                },
                loading = viewModel.loading,
                onGetDetails = { id, type ->
                    if (type == MediaType.Movie) {
                        MovieDetailsActivity.navigate(this, id)
                    } else {
                        SeriesDetailsActivity.navigate(this, id)
                    }
                },
                results = viewModel.searchContent
            )
        }
    }

}