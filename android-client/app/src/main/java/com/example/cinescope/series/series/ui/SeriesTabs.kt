package com.example.cinescope.series.series.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.series.series.SeriesScreenState

@Composable
fun SeriesTabs(
    state: SeriesScreenState,
    onError: () -> Unit
) {
    var tabIdx by remember { mutableStateOf(0) }
    val tabs = listOf("PTW", "Watching", "Watched") //TODO insert this strings in xml for translations

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
        0 -> PTWTab(state, onError)
        1 -> WatchingTab(state, onError)
        2 -> WatchedTab(state, onError)
    }
}