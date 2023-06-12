package com.example.cinescope.movies.movieDetails

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinescope.TAG
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.ui.BottomBar
import com.example.cinescope.ui.ImageUrl
import com.example.cinescope.ui.TopBar
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
                            Text(
                                text = state.movie.movieDetails.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .height(448.dp)
                                    .fillMaxWidth()
                            ) {
                                ImageUrl(path = state.movie.movieDetails.imgPath)
                            }
                            Card(
                                border = BorderStroke(2.dp, Color.Black),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    text = state.movie.movieDetails.description,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(text = "Duration: " + state.movie.movieDetails.duration + " min")
                            Card(
                                border = BorderStroke(2.dp, Color.Black),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Available in:",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(4.dp)
                                )
                                Row(modifier = Modifier.padding(4.dp)) {
                                    Text(text = "Streaming Services")
                                    if (state.movie.watchProviders.results.flatrate != null) {
                                        state.movie.watchProviders.results.flatrate.map { service ->
                                            Text(text = service.providerName)
                                            ImageUrl(path = service.logoPath)
                                        }
                                    }
                                }
                                Row(modifier = Modifier.padding(4.dp)) {
                                    Text(text = "Buy")
                                    state.movie.watchProviders.results.buy?.map { service ->
                                        Row {
                                            Log.v(TAG,service.providerName)
                                            Text(text = service.providerName)
                                            //ImageUrl(path = service.logoPath)
                                        }
                                    }
                                }
                                Row(modifier = Modifier.padding(4.dp)) {
                                    Text(text = "Renting Services")
                                    state.movie.watchProviders.results.rent?.map { service ->
                                        Row {
                                            Text(text = service.providerName)
                                            //ImageUrl(path = service.logoPath)
                                        }
                                    }
                                }
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


