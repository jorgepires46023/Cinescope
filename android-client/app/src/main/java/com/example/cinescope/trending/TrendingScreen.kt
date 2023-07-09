package com.example.cinescope.trending

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinescope.domain.MediaType
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.trending.ui.TrendingTabs
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.topbar.TopBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme

data class TrendingScreenState(
    val popMovies: List<MediaContent>?,
    val popSeries: List<MediaContent>?,
    val loading: Boolean,
    val error: String?
)

@Composable
fun TrendingScreen(
    state: TrendingScreenState,
    navController: NavController,
    onSearchRequested: () -> Unit,
    onGetDetails: (Int, MediaType) -> Unit,
    onError: () -> Unit
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
        ){
            innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ){
                Column {
                    TrendingTabs(state = state, onError, onGetDetails)
                }
            }
        }
    }
}