package com.example.cinescope.movies.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.ui.BottomBar
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.theme.CinescopeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movie: MovieInfo?,
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
                    onSearchRequested = onSearchRequested
                )
            }
        ){
            innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){
                Text(text = "Hey you've reached Movie Details Activity")
            }
        }
    }
}

