package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class SeasonDetails(
    @SerializedName("air_date")val airDate: String,
    val episodes: List<EpisodeDetails>,
    @SerializedName("season_number")val seasonNumber: Int
)