package com.example.cinescope.movies.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.images.ContentPoster
import com.example.cinescope.ui.cards.DescriptionCard
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.providers.WatchProviders
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme

data class MovieDetailsState(
    val movie: MovieInfo?,
    val loading: Boolean,
    val error: String?
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    navController: NavController,
    onSearchRequested: () -> Unit = { }
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = onSearchRequested
                )
            },
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!state.loading) {
                        if (state.movie != null) {
                            Title(title = state.movie.movieDetails.title)
                            ContentPoster(
                                imgPath = state.movie.movieDetails.imgPath,
                                height = 448.dp
                            )
                            Text(text = "Duration: " + state.movie.movieDetails.duration + " min")
                            DescriptionCard(state.movie.movieDetails.description)
                            if (state.movie.watchProviders.results.PT != null) { //TODO check if we should do something if there isn't any provider info
                                WatchProviders(providers = state.movie.watchProviders.results.PT)
                            }
                        } else {
                            Text(text = "Cannot Render Movie Details")
                        }
                    } else {
                        Text(text = "Loading...")
                    }
                }
            }
        }
    }
}


