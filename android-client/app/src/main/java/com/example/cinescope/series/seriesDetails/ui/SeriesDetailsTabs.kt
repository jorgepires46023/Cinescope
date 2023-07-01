package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.series.seriesDetails.SeasonData
import com.example.cinescope.series.seriesDetails.SeriesDetailsState
import com.example.cinescope.series.seriesDetails.SeriesUserData


@Composable
fun SeriesDetailsTabs(
    onError: () -> Unit,
    onTabChanged: (String) -> Unit,
    seasonData: SeasonData,
    seriesDetails: SeriesDetailsState,
    userData: SeriesUserData,
    loggedIn: Boolean
) {
    var tabIdx by remember { mutableIntStateOf(0) }
    val tabs = listOf("Details", "Seasons")
    TabRow(selectedTabIndex = tabIdx) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = tabIdx == index,
                onClick = {
                    onTabChanged(tab)
                    tabIdx = index
                },
                text = { Text(tab) }
            )
        }
    }
    when(tabIdx){
        0 -> SeriesDetailsTab(onError, seriesDetails, userData, loggedIn)
        1 -> SeriesSeasonsTab(seasonData, onError)
    }
}