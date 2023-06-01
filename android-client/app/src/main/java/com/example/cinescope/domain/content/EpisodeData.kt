package com.example.cinescope.domain.content

import com.google.gson.annotations.SerializedName

data class EpisodeData(
    @SerializedName("seriesID") val seriesId: Int,
    @SerializedName("imdbID") val imdbId: String,
    val name: String,
    @SerializedName("img") val imgPath: String,
    val season: Int,
    val episode: Int
)