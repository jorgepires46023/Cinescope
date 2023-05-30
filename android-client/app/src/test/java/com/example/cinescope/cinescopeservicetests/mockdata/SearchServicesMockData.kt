package com.example.cinescope.cinescopeservicetests.mockdata

import com.example.cinescope.domain.CompleteSearch
import com.example.cinescope.domain.MediaType
import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.services.dtos.ContentAPIDto
import com.example.cinescope.services.dtos.Results

internal val movie1 = Movie(1, "Movie1", "testImg1")
internal val movie2 = Movie(2, "Movie2", "testImg2")

internal val series1 = Series(1, "Series1", "testImg1")
internal val series2 = Series(2, "Series2", "testImg2")

private val resultMovie1 = Results(
    poster_path = "testImg1",
    id = 1,
    title = "Movie1",
    name = "Movie1",
    media_type = MediaType.Movie.type,
    popularity = 50
)

private val resultMovie2 = Results(
    poster_path = "testImg2",
    id = 2,
    title = "Movie2",
    name = "Movie2",
    media_type = MediaType.Movie.type,
    popularity = 99
)

private val resultSeries1 = Results(
    poster_path = "testImg1",
    id = 1,
    title = "Series1",
    name = "Series1",
    media_type = MediaType.Series.type,
    popularity = 50
)

private val resultSeries2 = Results(
    poster_path = "testImg2",
    id = 2,
    title = "Series2",
    name = "Series2",
    media_type = MediaType.Series.type,
    popularity = 99
)

private val moviesList = listOf(movie1, movie2)

private val seriesList = listOf(series1, series2)

internal const val wrongObjToMap = "Hello Cinescope"

internal val emptyMoviesList = listOf<Movie>()

internal val emptySeriesList = listOf<Series>()

internal val completeSearchObjWithEmptyLists = CompleteSearch(emptyMoviesList, emptySeriesList)

/** Building Results **/

private val emptyResultsList = listOf<Results>()

private val resultMoviesList = listOf(resultMovie1, resultMovie2)

private val resultSeriesList = listOf(resultSeries1, resultSeries2)

private val resultContentList = listOf(resultMovie1, resultSeries1, resultSeries2, resultMovie2)

/** Responses to enqueue in MockServer **/

internal val emptyResponse = ContentAPIDto(page = 0, results = emptyResultsList, total_results = 0, total_pages = 0)

internal val popMovieResponse = ContentAPIDto(page = 0, results = resultMoviesList, total_results = 2, total_pages = 0)

internal val recommendedMovieResponse = ContentAPIDto(page = 0, results = resultMoviesList, total_results = 2, total_pages = 0)

internal val popSeriesResponse = ContentAPIDto(page = 0, results = resultSeriesList, total_results = 2, total_pages = 0)

internal val recommendedSeriesResponse = ContentAPIDto(page = 0, results = resultSeriesList, total_results = 2, total_pages = 0)

internal val searchByQueryResponse = ContentAPIDto(page = 0, results = resultContentList, total_results = 2, total_pages = 0)

/** Expected results from SearchService methods **/

internal val expectedPopularMovies = listOf(movie1, movie2)

internal val expectedRecommendedMovies = listOf(movie1, movie2)

internal val expectedPopularSeries = listOf(series1, series2)

internal val expectedRecommendedSeries = listOf(series1, series2)

internal val expectedCompleteSearch = CompleteSearch(moviesList, seriesList)
