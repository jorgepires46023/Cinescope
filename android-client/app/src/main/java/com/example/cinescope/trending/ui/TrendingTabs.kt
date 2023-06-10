package com.example.cinescope.trending.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.trending.TrendingScreenState

@Composable
fun TrendingTabs(
    state: TrendingScreenState,
    onError: () -> Unit,
    onGetMovieDetails: (id: Int) -> Unit,
    onGetSeriesDetails: (id: Int) -> Unit
) {
    var tabIdx by remember { mutableStateOf(0) }
    val tabs = listOf("Movies", "Series") //TODO insert this strings in xml for translations

    TabRow(selectedTabIndex = tabIdx) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = tabIdx == index,
                onClick = { tabIdx = index },
                text = { Text(title) }
            )
        }
    }
    when(tabIdx){
        0 -> MoviesTab(state = state, onError, onGetMovieDetails)
        1 -> SeriesTab(state = state, onError, onGetSeriesDetails)
    }
}