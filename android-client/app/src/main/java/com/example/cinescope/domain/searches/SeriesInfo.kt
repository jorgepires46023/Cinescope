package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class SeriesInfo(
    @SerializedName("serieDetails") val seriesDetails: SeriesDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds
)