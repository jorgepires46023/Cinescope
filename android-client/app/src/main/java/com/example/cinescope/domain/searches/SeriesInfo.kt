package com.example.cinescope.domain.searches

data class SeriesInfo(
    val seriesDetails: SeriesDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds
)