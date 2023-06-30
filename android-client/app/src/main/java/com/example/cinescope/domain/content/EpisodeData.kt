package com.example.cinescope.domain.content

import com.google.gson.annotations.SerializedName

data class EpisodeData(
    val epId: Int,
    val seriesId: Int,
    @SerializedName("epimdbId") val imdbId: String,
    val name: String,
    @SerializedName("img") val imgPath: String,
    val season: Int,
    val episode: Int
)