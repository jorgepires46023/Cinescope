package com.example.cinescope.domain.searches

import com.example.cinescope.domain.MediaType

data class MediaContent(val id: Int, val title: String, val imgPath: String?, val mediaType: MediaType)