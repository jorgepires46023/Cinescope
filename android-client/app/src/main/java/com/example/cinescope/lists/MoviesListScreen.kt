package com.example.cinescope.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.grid.MovieDataGrid
import com.example.cinescope.ui.theme.CinescopeTheme

@Composable
fun MoviesListScreen(
    navController: NavController,
    moviesList: List<MovieData>?,
    onGetMovieDetails: (Int) -> Unit,
    onBackRequest: () -> Unit
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = null
                )
            },
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ){ innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { onBackRequest() }) {
                            Text(text = "Back")
                        }
                    }
                    if(!moviesList.isNullOrEmpty()){
                        Title(title = "Movies List:")//
                        Spacer(modifier = Modifier.height(8.dp))
                        MovieDataGrid(list = moviesList, onGetDetails = onGetMovieDetails)
                    } else {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Empty List",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}