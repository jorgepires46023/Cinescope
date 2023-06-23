package com.example.cinescope.domain.content

import com.google.gson.annotations.SerializedName

data class SeriesData(
    val tmdbId: Int,
    val imdbId: String,
    val name: String,
    @SerializedName("img") val imgPath: String,
    @SerializedName("EpListId") val epListId: Int,
    val state: String
)