package com.example.cinescope.episodes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.utils.viewModelInit

class EpisodeDetailsActivity: ComponentActivity() {

    private val dependencies by lazy { application as DependenciesContainer }
    companion object{
        private const val SERIES_ID = "SERIES_ID"
        private const val SEASON_NR = "SEASON_NR"
        private const val EPISODE_NR = "EPISODE_NR"
        private const val IS_WATCHED = "IS_WATCHED"
        fun navigate(origin: Context, seriesId:Int, seasonNr: Int, episodeNr: Int, isWatched: Boolean){
            with(origin){
                val intent = Intent(this, EpisodeDetailsActivity::class.java)
                intent.putExtra(SERIES_ID, seriesId.toString())
                intent.putExtra(SEASON_NR, seasonNr.toString())
                intent.putExtra(EPISODE_NR, episodeNr.toString())
                intent.putExtra(IS_WATCHED, isWatched.toString())
                startActivity(intent)
            }
        }
    }

    private val viewModel: EpisodeDetailsViewModel by viewModels{
        viewModelInit {
            EpisodeDetailsViewModel(dependencies.searchServices, dependencies.seriesServices)
        }
    }

    private val seriesId: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(SERIES_ID))
        id.toInt()
    }

    private val seasonNr: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(SEASON_NR))
        id.toInt()
    }

    private val episodeNr: Int by lazy {
        val id = checkNotNull(intent.getStringExtra(EPISODE_NR))
        id.toInt()
    }

    private val isWatched: Boolean by lazy {
        val id = checkNotNull(intent.getStringExtra(IS_WATCHED))
        id.toBoolean()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = dependencies.userRepo.user
        if(user != null)
            viewModel.changeWatchState(isWatched)
        viewModel.getEpisodeDetails(seriesId, seasonNr, episodeNr)

        setContent {
            EpisodeDetailsScreen(
                episodeDetails = viewModel.episode,
                isWatched = viewModel.watchedEpisode,
                onAddWatchedEpisode = { if(user != null)
                    viewModel.addWatchedEpisode(
                        seriesId,
                        seasonNr,
                        episodeNr,
                        user.cookie
                    )
                },
                onDeleteWatchedEpisode = {
                    if (user != null)
                        viewModel.deleteWatchedEpisode(
                            seriesId,
                            seasonNr,
                            episodeNr,
                            user.cookie
                        )
                },
                loggedIn = user != null,
                onSearchRequested = { SearchActivity.navigate(this)},
                navController = dependencies.navController,
                isLoading = viewModel.loading
            )
        }
    }
}