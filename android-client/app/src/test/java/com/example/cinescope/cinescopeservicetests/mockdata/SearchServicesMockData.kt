package com.example.cinescope.cinescopeservicetests.mockdata

import com.example.cinescope.domain.Movie
import com.example.cinescope.service.dtos.PopularDto
import com.example.cinescope.service.dtos.Results

enum class MediaType(val type: String) {
    Movie("movie"), TVSHOW("tv")
}

internal val movie1 = Movie(1, "Movie1", "testImg1")
internal val movie2 = Movie(2, "Movie2", "testImg2")

private val result1 = Results(
    poster_path = "testImg1",
    id = 1,
    title = "Movie1",
    name = "Movie1",
    media_type = MediaType.Movie.type,
    popularity = 50
)

private val result2 = Results(
    poster_path = "testImg2",
    id = 2,
    title = "Movie2",
    name = "Movie2",
    media_type = MediaType.Movie.type,
    popularity = 99
)

internal val wrongObjToMap = "Hello Cinescope"

private val moviesList = listOf(result1, result2)

internal val popMovieResponse = PopularDto(page = 0, results = moviesList, total_results = 2, total_pages = 0)

internal val expectedPopularMovies = listOf(movie1, movie2)