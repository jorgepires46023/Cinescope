package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class SeriesDetails(
    val id: Int,
    val name: String,
    val seasons: List<SeasonDetails>,
    val status: String,
    @SerializedName("poster_path") val imgPath: String,
    @SerializedName("overview") val description: String
)