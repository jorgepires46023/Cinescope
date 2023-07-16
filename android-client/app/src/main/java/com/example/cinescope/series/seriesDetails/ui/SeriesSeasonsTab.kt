package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
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
import com.example.cinescope.series.seriesDetails.SeasonData
import com.example.cinescope.ui.errors.AlertError
import com.example.cinescope.ui.providers.WatchProviders

@Composable
fun SeriesSeasonsTab(
    error: String?,
    seasonData: SeasonData,
    onError: () -> Unit,
    onNavigateToEpisode: (Int, Int, Boolean) -> Unit,
    loading: Boolean
) {
    if(seasonData.seasons != null) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            seasonData.seasons.forEach {s ->
                var expandable by rememberSaveable { mutableStateOf(false) }
                Button(
                    onClick = {
                        expandable = !expandable
                        if(expandable)
                            seasonData.onGetSeasonDetails(s.seasonNumber)
                    },
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Season ${s.seasonNumber}")
                    Spacer(modifier = Modifier.width(8.dp))
                    if(!expandable)
                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
                    else
                        Icon(imageVector = Icons.Default.ExpandLess, contentDescription = null)
                }

                if(expandable){
                    if(!loading) {
                        val season = seasonData.seasonsDetails[s.seasonNumber]
                        if (season?.seasonDetails?.episodes != null && seasonData.watchedEpisodeList != null)
                            EpisodeList(
                                seasonDetails = season.seasonDetails,
                                watchedEpisodeList = seasonData.watchedEpisodeList,
                                onAddWatchedEpisode = seasonData.onAddWatchedEpisode,
                                onDeleteWatchedEpisode = seasonData.onDeleteWatchedEpisode,
                                onNavigateToEpisode = { episodeNr, isWatched ->
                                    onNavigateToEpisode(
                                        season.seasonDetails.seasonNumber,
                                        episodeNr,
                                        isWatched
                                    )
                                }
                            )
                        WatchProviders(providers = season?.watchProviders?.results?.PT)
                    }
                    else Text(text = "Loading")
                }
            }
            if(error != null) AlertError(error = error, dismiss = onError)
        }
    }
}