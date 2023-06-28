package com.example.cinescope.series.seriesDetails

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.utils.viewModelInit

class SeriesDetailsActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }
    companion object{
        private const val SERIES_ID = "SERIES_ID"
        fun navigate(origin: Context, seriesId: Int){
            with(origin){
                val intent = Intent(this, SeriesDetailsActivity::class.java)
                intent.putExtra(SERIES_ID, seriesId.toString())
                startActivity(intent)
            }
        }
    }
    private val viewModel: SeriesDetailsScreenViewModel by viewModels {
        viewModelInit{
            SeriesDetailsScreenViewModel(dependencies.searchServices, dependencies.seriesServices)
        }
    }

    private val seriesId: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(SERIES_ID))
        id.toInt()
    }

    override fun onResume() {
        super.onResume()
        val user = dependencies.userRepo.user
        if(user != null)
            viewModel.getSeriesUserData(seriesId, user.cookie)
        viewModel.getSeriesDetails(seriesId)

        setContent{
            SeriesDetailsScreen(
                state = SeriesDetailsState(
                    series = viewModel.series,
                    loading = viewModel.loading,
                    error = viewModel.error,
                    seriesData = viewModel.userData
                ),
                userData = SeriesUserData(
                    seriesData = viewModel.userData,
                    lists = viewModel.lists,
                    onAddToList = { listId ->
                        if(user != null) viewModel.addSeriesToList(listId, seriesId, user.cookie)
                    },
                    onDeleteFromList = { listId ->
                        if(user != null) viewModel.deleteSeriesFromList(listId, seriesId, user.cookie)
                    },
                    onGetLists = {
                        if(user != null) viewModel.getLists(user.cookie)
                    }
                ),
                navController = dependencies.navController,
                loggedIn = user != null,
                onChangeState = {state ->
                    if(user != null) viewModel.changeState(state, seriesId, user.cookie)
                },
                onSearchRequested = { SearchActivity.navigate(this)}
            )
        }
    }
}