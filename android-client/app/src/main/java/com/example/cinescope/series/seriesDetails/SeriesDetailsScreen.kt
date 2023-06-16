package com.example.cinescope.series.seriesDetails

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
import com.example.cinescope.domain.searches.SeriesInfo
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.ContentPoster
import com.example.cinescope.ui.DescriptionCard
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.WatchProviders
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme

data class SeriesDetailsState(
    val series: SeriesInfo?,
    val loading: Boolean,
    val error: String?
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesDetailsScreen(
    state: SeriesDetailsState,
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
                    onSearchRequested = onSearchRequested,
                    navController = navController
                )
            }
        ){ innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!state.loading) {
                        if (state.series != null) {
                            Title(title = state.series.seriesDetails.name)
                            ContentPoster(
                                imgPath = state.series.seriesDetails.imgPath,
                                height = 448.dp
                            )
                            DescriptionCard(desc = state.series.seriesDetails.description)
                            if (state.series.watchProviders.results.PT != null) { //TODO check if we should do something if there isn't any provider info
                                WatchProviders(providers = state.series.watchProviders.results.PT)
                            }
                        } else {
                            Text(text = "Cannot Render Series Details")
                        }
                    } else {
                        Text(text = "Loading...")
                    }
                }
            }
        }
    }
}