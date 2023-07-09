package com.example.cinescope.movies.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.MovieState
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.images.ContentPoster
import com.example.cinescope.ui.cards.DescriptionCard
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.topbar.TopBar
import com.example.cinescope.ui.providers.WatchProviders
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.dropdown.Dropdown
import com.example.cinescope.ui.floatingbutton.FloatingButton
import com.example.cinescope.ui.theme.CinescopeTheme

data class MovieDetailsState(
    val movie: MovieInfo?,
    val loading: Boolean,
    val error: String?
)
data class MovieUserData(
    val movieData : UserDataContent?,
    val lists: List<ContentList>?,
    val onAddToList: (Int) -> Unit,
    val onDeleteFromList: (Int) -> Unit,
    val onGetLists:  () -> Unit
)
@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    userData: MovieUserData,
    navController: NavController,
    onSearchRequested: () -> Unit,
    loggedIn: Boolean,
    onChangeState: (String) -> Unit
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
                    FloatingButton(
                        lists = userData.lists,
                        onGetLists =  userData.onGetLists,
                        onAddToList = userData.onAddToList,
                        onDeleteFromList = userData.onDeleteFromList,
                        userData = userData.movieData
                    )
            },
            floatingActionButtonPosition = FabPosition.End,
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
                            if(loggedIn){
                                val currentState = if (userData.movieData != null)
                                    userData.movieData.state ?:  MovieState.NO_STATE.state
                                else
                                    MovieState.NO_STATE.state
                                Dropdown(context = "Movie State", currentState, MovieState.getStates(), onChangeState)
                            }
                            WatchProviders(providers = state.movie.watchProviders.results.PT)
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


