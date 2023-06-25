package com.example.cinescope.lists

import com.example.cinescope.domain.content.ContentList

data class MovieActions(
    val onCreateMovieList: (String) -> Unit,
    val moviesLists: List<ContentList>?,
    val onUpdateMoviesLists: () -> Unit,
    val deleteMovieList: (Int) -> Unit
)