package com.example.cinescope.domain.content

import com.google.gson.annotations.SerializedName

data class SeriesData(
    @SerializedName("tmdbID") val tmdbId: Int,
    @SerializedName("imdbID") val imdbId: String,
    val name: String,
    @SerializedName("img") val imgPath: String,
    @SerializedName("EpListId") val epListId: Int,
    val state: String
)