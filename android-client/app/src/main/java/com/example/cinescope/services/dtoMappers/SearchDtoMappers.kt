package com.example.cinescope.services.dtoMappers

import com.example.cinescope.domain.searches.SearchContent
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.domain.MediaType
import com.example.cinescope.domain.searches.Movie
import com.example.cinescope.domain.searches.Series
import com.example.cinescope.services.dtos.ContentAPIDto
import com.example.cinescope.services.dtos.Results

fun ContentAPIDto.toMovies(): List<Movie> {
    val moviesList = mutableListOf<Movie>()
    this.results.forEach{
        val content = checkAndAdd(it)
        if(content is Movie) moviesList.add(content)
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

fun ContentAPIDto.toContent(): SearchContent {
    val seriesList = mutableListOf<Series>()
    val moviesList = mutableListOf<Movie>()

    this.results.forEach {
        val content = checkAndAdd(it)
        if(content is Series) seriesList.add(content)
        if(content is Movie) moviesList.add(content)
    }

    return SearchContent(moviesList,seriesList)
}
