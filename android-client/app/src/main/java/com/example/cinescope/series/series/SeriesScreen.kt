package com.example.cinescope.series.series

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.series.series.ui.SeriesTabs
import com.example.cinescope.ui.topbar.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme

data class SeriesScreenState(
    val ptwSeries: List<SeriesData>?,
    val watchingSeries: List<SeriesData>?,
    val watchedSeries: List<SeriesData>?,
    val loading: Boolean
)

@Composable
fun SeriesScreen(
    state: SeriesScreenState,
    navController: NavController,
    onSearchRequested: () -> Unit,
    error: String?,
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
                    SeriesTabs(state = state, error, onError, onGetDetails, onTabChanged)
                }
            }
        }
    }
}

