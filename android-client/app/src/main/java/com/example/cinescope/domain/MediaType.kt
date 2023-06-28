package com.example.cinescope.domain

import java.lang.Exception

enum class MediaType(val type: String) {
    Movie("movie"), Series("tv");

    companion object {
        fun fromString(str: String): MediaType {
            return when(str) {
                Movie.type-> Movie
                Series.type -> Series
                else -> { throw Exception("MediaType not Supported") }
            }
        }
    }
}