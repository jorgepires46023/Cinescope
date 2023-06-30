package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.searches.Season
import com.example.cinescope.domain.searches.SeasonDetails
import com.example.cinescope.domain.searches.SeasonInfo
import com.example.cinescope.series.seriesDetails.SeriesDetailsState


@Composable
fun SeriesDetailsTabs(
    onError: () -> Unit,
    onGetDetails: (Int) -> Unit, //TODO eliminar esta mierda millio
    onTabChanged: (String) -> Unit,
    state: SeriesDetailsState,
    loggedIn: Boolean,
    onChangeState: (String) -> Unit,
    seasonList: List<Season>?,
    seasonDetails: SeasonInfo?,
    watchedEpisodeList: List<EpisodeData>?,
    onGetSeasonDetails: (Int) -> Unit
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
        0 -> SeriesDetailsTab( onError, onGetDetails, state, loggedIn, onChangeState)
        1 -> SeriesSeasonsTab( onError, onGetSeasonDetails, seasonList, seasonDetails,watchedEpisodeList, {}, {})
    }
}