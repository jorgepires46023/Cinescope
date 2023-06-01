package com.example.cinescope.domain.searches

data class MovieInfo(
    val movieDetails: MovieDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds
)