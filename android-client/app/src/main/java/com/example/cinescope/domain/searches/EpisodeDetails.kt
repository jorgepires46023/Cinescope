package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class EpisodeDetails(
    val id: Int,
    val name: String,
    @SerializedName("air_date")val airDate: String,
    @SerializedName("episode_number")val episodeNumber: Int,
    @SerializedName("overview")val description: String,
    @SerializedName("still_path")val epImgPath: String
)