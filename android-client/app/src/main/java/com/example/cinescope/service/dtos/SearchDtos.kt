package com.example.cinescope.service.dtos

data class PopularDto(
    val page: Int,
    val results: List<Results>,
    val total_results: Int,
    val total_pages: Int
)

data class Results(
    val poster_path: String,
    val id: Int,
    val title: String,
    val name: String,
    val media_type: String,
    val popularity: Int
)