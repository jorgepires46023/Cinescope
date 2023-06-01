package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Int,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_title") val name: String,
    @SerializedName("overview") val description: String,
    @SerializedName("poster_path") val imgPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val duration: String,
    val status: String,
    val title: String
)