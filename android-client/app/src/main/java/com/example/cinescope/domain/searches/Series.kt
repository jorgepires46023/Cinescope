package com.example.cinescope.domain.searches

data class Series(val seriesId: Int, val _name: String, val _imgPath: String )
    : MediaContent(id = seriesId, name = _name, imgPath = _imgPath)