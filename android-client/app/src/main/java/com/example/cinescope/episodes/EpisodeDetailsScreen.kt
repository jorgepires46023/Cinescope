package com.example.cinescope.episodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.searches.EpisodeInfo
import com.example.cinescope.episodes.ui.EpisodeFloatingButton
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.cards.DescriptionCard
import com.example.cinescope.ui.floatingbutton.FloatingButton
import com.example.cinescope.ui.images.ContentPoster
import com.example.cinescope.ui.theme.CinescopeTheme
import com.example.cinescope.ui.topbar.TopBar

@Composable
fun EpisodeDetailsScreen(
    episodeDetails: EpisodeInfo?,
    isLoading: Boolean,
    isWatched: Boolean,
    onAddWatchedEpisode: () -> Unit,
    onDeleteWatchedEpisode: () -> Unit,
    loggedIn: Boolean,
    onSearchRequested: () -> Unit,
    navController: NavController
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = onSearchRequested
                )
            },
            floatingActionButton = {
                if (loggedIn)
                    EpisodeFloatingButton(
                        onAddWatched = onAddWatchedEpisode,
                        onDeleteWatched = onDeleteWatchedEpisode,
                        isWatched = isWatched
                    )
            },
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if (!isLoading) {
                        if(episodeDetails?.episodeDetails != null){
                            Title(title = episodeDetails.episodeDetails.name)
                            ContentPoster(
                                imgPath = episodeDetails.episodeDetails.epImgPath,
                                height = 228.dp
                            )
                            Text(text = "Release date: ${episodeDetails.episodeDetails.airDate}")
                            if (episodeDetails.episodeDetails.description.isNotBlank())
                                DescriptionCard(episodeDetails.episodeDetails.description)
                        }else {
                            Text(text = "Loading...")
                        }
                    }
                }
            }
        }
    }
}