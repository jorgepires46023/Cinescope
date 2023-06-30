package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class Season(
    val id: Int,
    val name: String,
    @SerializedName("episodeCount") val totalEpisodes: Int,
    @SerializedName("season_number") val seasonNumber: Int
)