package com.example.cinescope.domain.searches

data class Movie(val movieId: Int, val _name: String, val _imgPath: String )
    : MediaContent(id = movieId, name = _name, imgPath = _imgPath)