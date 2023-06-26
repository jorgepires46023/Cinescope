package com.example.cinescope.movies.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.movies.movies.ui.MoviesTabs
import com.example.cinescope.trending.ui.TrendingMoviesTab
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme


data class MoviesScreenState(
    val ptwMovies: List<MovieData>?,
    val watchedMovies: List<MovieData>?,
    val error: String?,
    val loading: Boolean
)

@Composable
fun MoviesScreen(
    state: MoviesScreenState,
    navController: NavController,
    onSearchRequested: () -> Unit = {},//TODO remove this default function
    onError: () -> Unit = {},
    onTabChanged: (String) -> Unit,
    onGetDetails: (Int) -> Unit
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
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Column {
                    MoviesTabs(state = state, onError, onGetDetails, onTabChanged)
                }
            }
        }
    }
}

