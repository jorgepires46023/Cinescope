package com.example.cinescope.trending.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.MediaType
import com.example.cinescope.trending.TrendingScreenState

@Composable
fun TrendingTabs(
    state: TrendingScreenState,
    onError: () -> Unit,
    onGetDetails: (Int, MediaType) -> Unit,
) {
    var tabIdx by remember { mutableIntStateOf(0) }
    val tabs = listOf("Movies", "Series")

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
        0 -> TrendingMoviesTab(state = state, onError, onGetDetails)
        1 -> TrendingSeriesTab(state = state, onError, onGetDetails)
    }
}