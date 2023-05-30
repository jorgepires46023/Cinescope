package com.example.cinescope.services.dtos

import com.example.cinescope.domain.Movie
import com.google.gson.annotations.SerializedName

data class MovieListId(val id: Int)
data class MovieState(val state: String)

data class MovieUserDataDto(val moviesList: List<MovieUserData>)
data class MovieUserData(
    val name: String,
    val imdbID: String,
    @SerializedName("tmdbID")val tmdbId: Int,
    val img: String,
    val state: String
)

