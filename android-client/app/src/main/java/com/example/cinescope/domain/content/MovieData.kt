package com.example.cinescope.domain.content

import com.google.gson.annotations.SerializedName

class MovieData(
    @SerializedName("tmdbID") val tmdbId: Int,
    @SerializedName("imdbID") val imdbId: String,
    @SerializedName("img") val imgPath: String,
    val name: String,
    val state: String
)