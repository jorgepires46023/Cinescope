package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.runtime.Composable
import com.example.cinescope.domain.searches.EpisodeDetails
import com.example.cinescope.ui.list.ListItemCheckbox

@Composable
fun EpisodeItem(
    watchedEpisode: Boolean,
    episodeDetails: EpisodeDetails,
    onAddWatchedEpisode: (Int) -> Unit,
    onDeleteWatchedEpisode: (Int) -> Unit,
) {
    ListItemCheckbox(
        name = "${episodeDetails.episodeNumber}: ${episodeDetails.name} " ,
        onAdd = { onAddWatchedEpisode(episodeDetails.episodeNumber) },
        onDelete = { onDeleteWatchedEpisode(episodeDetails.episodeNumber) },
        checked = watchedEpisode,
        onUpdate = {}
    )
}