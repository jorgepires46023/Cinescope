package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.searches.Season
import com.example.cinescope.domain.searches.SeasonDetails
import com.example.cinescope.domain.searches.SeasonInfo
import com.example.cinescope.ui.providers.WatchProviders

@Composable
fun SeriesSeasonsTab(
    onError: () -> Unit,
    onGetSeasonDetails: (Int) -> Unit,
    seasonList: List<Season>?,
    seasonDetails: SeasonInfo?,
    watchedEpisodeList: List<EpisodeData>?,
    onAddWatchedEpisode: (Int) -> Unit,
    onDeleteWatchedEpisode: (Int) -> Unit
) {
    var expandable by rememberSaveable { mutableStateOf(false) }
    var currentSeason by rememberSaveable { mutableStateOf(seasonList?.find { it.seasonNumber == 1 }?.seasonNumber)} //TODO tanto aqui como activity const
    if(seasonList != null) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { if(seasonList.size > 1) expandable = !expandable },
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Season $currentSeason")
                Spacer(modifier = Modifier.width(8.dp))
                if(!expandable)
                    Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
                else
                    Icon(imageVector = Icons.Default.ExpandLess, contentDescription = null)
            }
            DropdownMenu(
                expanded = expandable,
                onDismissRequest = { expandable = false },
                modifier = Modifier
                    .focusable(false)
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(), //TODO
                scrollState = rememberScrollState()
            ) {
                seasonList.forEach { season ->
                    DropdownMenuItem(
                        text = { Text(text = " Season ${season.seasonNumber} ") },
                        onClick = {
                            onGetSeasonDetails(season.seasonNumber)
                            currentSeason = season.seasonNumber
                            expandable = !expandable
                        }
                    )
                }
            }
            val episodeList = watchedEpisodeList?.filter { it.season == currentSeason }
            seasonDetails?.seasonDetails?.episodes?.forEach{ episode ->
                EpisodeItem(
                    watchedEpisode = episodeList?.find {
                        it.episode == episode.episodeNumber && it.season == currentSeason
                    } != null,
                    episodeDetails = episode,
                    onAddWatchedEpisode = onAddWatchedEpisode,
                    onDeleteWatchedEpisode = onDeleteWatchedEpisode
                )
            }
            if (seasonDetails?.watchProviders?.results?.PT != null) { //TODO check if we should do something if there isn't any provider info
                WatchProviders(providers = seasonDetails.watchProviders.results.PT)
            }
        }
    }
}