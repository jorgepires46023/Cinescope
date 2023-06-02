package com.example.cinescope.domain.content

import com.google.gson.annotations.SerializedName

data class EpisodeData(
    @SerializedName("epID") val epId: Int,
    @SerializedName("seriesID") val seriesId: Int,
    @SerializedName("imdbID") val imdbId: String,
    val name: String,
    @SerializedName("img") val imgPath: String,
    val seasonNr: Int,
    val episodeNr: Int
)