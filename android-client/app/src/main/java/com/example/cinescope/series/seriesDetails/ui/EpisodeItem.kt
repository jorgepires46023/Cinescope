package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.cinescope.domain.searches.EpisodeDetails

@Composable
fun EpisodeItem(
    episode: EpisodeDetails,
    onAddWatchedEpisode: () -> Unit,
    onDeleteWatchedEpisode: () -> Unit,
    watched: Boolean,
    onClick: () -> Unit = {}
) {
    var checkedState by remember { mutableStateOf(watched) }
    ListItem(
        headlineContent = { Text(text = episode.name) },
        trailingContent = {
            Checkbox(
                checked =  checkedState,
                onCheckedChange = {
                    checkedState = if (checkedState) {
                        onDeleteWatchedEpisode()
                        false
                    }else{
                        onAddWatchedEpisode()
                        true
                    }
                }
            )
        },
        modifier = Modifier.clickable { onClick() }
    )
    Divider()

}