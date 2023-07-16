package com.example.cinescope.movies.movies.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.MovieState
import com.example.cinescope.movies.movies.MoviesScreenState

data class MoviesTabsInfo(val title: String, val state: String)

@Composable
fun MoviesTabs(
    state: MoviesScreenState,
    error: String?,
    onError: () -> Unit,
    onGetDetails: (Int) -> Unit,
    onTabChanged: (String) -> Unit
) {
    var tabIdx by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        MoviesTabsInfo(MovieState.PTW.name, MovieState.PTW.state),
        MoviesTabsInfo(MovieState.WATCHED.name, MovieState.WATCHED.state)
    )

    TabRow(selectedTabIndex = tabIdx) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = tabIdx == index,
                onClick = {
                    onTabChanged(tab.state)
                    tabIdx = index
                },
                text = { Text(tab.title) }
            )
        }
    }
    when(tabIdx){
        0 -> MoviesPTWTab(state, error, onError, onGetDetails)
        1 -> MoviesWatchedTab(state, error, onError, onGetDetails)
    }
}