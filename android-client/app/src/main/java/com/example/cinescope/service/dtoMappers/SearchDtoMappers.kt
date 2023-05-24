package com.example.cinescope.service.dtoMappers

import com.example.cinescope.domain.Movie
import com.example.cinescope.service.dtos.PopularDto

fun PopularDto.toMovies() =
    this.results.map{
        Movie(
            movieId = it.id,
            name = it.title,
            img = it.poster_path
        )
    }