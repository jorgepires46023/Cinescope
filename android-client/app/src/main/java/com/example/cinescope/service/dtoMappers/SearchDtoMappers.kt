package com.example.cinescope.service.dtoMappers

import com.example.cinescope.domain.CompleteSearch
import com.example.cinescope.domain.MediaContent
import com.example.cinescope.domain.MediaType
import com.example.cinescope.domain.Movie
import com.example.cinescope.domain.Series
import com.example.cinescope.service.dtos.ContentAPIDto
import com.example.cinescope.service.dtos.Results

fun ContentAPIDto.toMovies(): List<Movie> {
    val moviesList = mutableListOf<Movie>()
    this.results.forEach{
        val content = checkAndAdd(it)
        if(content is Movie) moviesList.add(content as Movie)
    }
    return moviesList
}


fun ContentAPIDto.toSeries(): List<Series> {
    val seriesList = mutableListOf<Series>()
    this.results.forEach{
        val content = checkAndAdd(it)
        if(content is Series) seriesList.add(content)
    }
    return seriesList
}

private fun checkAndAdd(results: Results): MediaContent?{
    if(results.media_type == MediaType.Series.type){
        return Series(
            seriesId = results.id,
            _name = results.name,
            _imgPath = results.poster_path
        )
    }
    if(results.media_type == MediaType.Movie.type){
        return Movie(
            movieId = results.id,
            _name = results.name,
            _imgPath = results.poster_path
        )
    }
    return null
}

fun ContentAPIDto.toContent(): CompleteSearch {
    val seriesList = mutableListOf<Series>()
    val moviesList = mutableListOf<Movie>()

    this.results.forEach {
        val content = checkAndAdd(it)
        if(content is Series) seriesList.add(content)
        if(content is Movie) moviesList.add(content)
    }

    return CompleteSearch(moviesList,seriesList)
}
