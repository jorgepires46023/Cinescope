package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.runtime.Composable
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.searches.SeasonDetails

@Composable
fun EpisodeList(
    seasonDetails: SeasonDetails,
    watchedEpisodeList: List<EpisodeData>,
    onAddWatchedEpisode: (Int, Int) -> Unit,
    onDeleteWatchedEpisode: (Int, Int) -> Unit,
    onNavigateToEpisode: (Int, Boolean) -> Unit

    ) {
    seasonDetails.episodes.forEach { episode ->
        val isWatched = watchedEpisodeList.find { it.episode == episode.episodeNumber && it.season == seasonDetails.seasonNumber } != null
        EpisodeItem(
            episode = episode,
            onAddWatchedEpisode = { onAddWatchedEpisode(seasonDetails.seasonNumber, episode.episodeNumber) },
            onDeleteWatchedEpisode = { onDeleteWatchedEpisode(seasonDetails.seasonNumber, episode.episodeNumber) },
            watched = isWatched,
            onClick = {onNavigateToEpisode(episode.episodeNumber, isWatched)}
        )
    }
}