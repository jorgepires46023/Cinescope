package com.example.cinescope.series.seriesDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.episodes.EpisodeDetailsActivity
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
    private val viewModel: SeriesDetailsViewModel by viewModels {
        viewModelInit{
            SeriesDetailsViewModel(dependencies.searchServices, dependencies.seriesServices)
        }
    }

    private val seriesId: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(SERIES_ID))
        id.toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = dependencies.userRepo.user
        if(user != null) {
            viewModel.getSeriesUserData(seriesId, user.cookie)
            viewModel.getWatchedEpisodeList(seriesId, user.cookie)
        }
        viewModel.getSeriesDetails(seriesId)

        setContent{
            SeriesDetailsScreen(
                seriesDetails = SeriesDetailsState(
                    series = viewModel.series,
                    loading = viewModel.loading,
                    error = viewModel.error
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
                    },
                    onChangeState = {state ->
                        if(user != null) viewModel.changeState(state, seriesId, user.cookie)
                    },
                    onUpdate = { /*if(user != null) viewModel.getSeriesUserData(seriesId, user.cookie)*/ }
                ),
                navController = dependencies.navController,
                loggedIn = user != null,
                onSearchRequested = { SearchActivity.navigate(this)},
                onTabChanged = {tab ->
                    if(tab == "Details"){
                        viewModel.getSeriesDetails(seriesId = seriesId)
                    }
                    else if(tab == "Seasons") {
                        if(user != null) viewModel.getWatchedEpisodeList(seriesId, user.cookie)
                    }
                },
                seasonData = SeasonData(
                    seasons = viewModel.series?.seriesDetails?.seasons,
                    watchedEpisodeList = viewModel.watchedEpisodes,
                    seasonsDetails = viewModel.seasons,
                    onGetSeasonDetails = { seasonNr -> viewModel.getSeasonDetails(seriesId, seasonNr) },
                    onAddWatchedEpisode = { seasonNr,  episodeNr ->
                        if(user != null)
                            viewModel.addWatchedEpisode(seriesId, seasonNr, episodeNr, user.cookie)
                    },
                    onDeleteWatchedEpisode = { seasonNr,  episodeNr ->
                        if(user != null)
                            viewModel.deleteWatchedEpisode(seriesId, seasonNr, episodeNr, user.cookie)
                    }
                ),
                onNavigateToEpisode = {seasonNr, episodeNr, isWatched ->
                    EpisodeDetailsActivity.navigate(this, seriesId, seasonNr, episodeNr, isWatched)
                },
                loading = viewModel.loading
            )
        }
    }
}