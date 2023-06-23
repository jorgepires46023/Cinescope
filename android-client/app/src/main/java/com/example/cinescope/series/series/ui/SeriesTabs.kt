package com.example.cinescope.series.series.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.SeriesState
import com.example.cinescope.series.series.SeriesScreenState

data class SeriesTabsInfo(val title: String, val state: String)
@Composable
fun SeriesTabs(
    state: SeriesScreenState,
    onError: () -> Unit,
    onGetDetails: (Int) -> Unit,
    onTabChanged: (String) -> Unit
) {
    var tabIdx by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        SeriesTabsInfo("PTW", SeriesState.PTW.state),
        SeriesTabsInfo("Watching", SeriesState.WATCHING.state),
        SeriesTabsInfo("Watched", SeriesState.WATCHED.state)
    ) //TODO insert this strings in xml for translations

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
        0 -> PTWTab(state, onError, onGetDetails)
        1 -> WatchingTab(state, onError, onGetDetails)
        2 -> WatchedTab(state, onError, onGetDetails)
    }
}