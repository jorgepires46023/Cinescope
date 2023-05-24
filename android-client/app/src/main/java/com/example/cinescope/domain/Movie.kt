package com.example.cinescope.domain

data class Movie(val movieId: Int, val name: String, val img: String) //TODO check all domain data classes

// Parameter img must be String because TMDB api needs to add another path before this imgURL