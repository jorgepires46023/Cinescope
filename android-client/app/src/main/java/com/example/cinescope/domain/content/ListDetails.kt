package com.example.cinescope.domain.content

data class MovieListDetails(val info: Info, val results: List<MovieData>)

data class SeriesListDetails(val info: Info, val results: List<SeriesData>)

data class Info(
    val name: String,
    val id: Int
)