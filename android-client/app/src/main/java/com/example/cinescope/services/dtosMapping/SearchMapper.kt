package com.example.cinescope.services.dtosMapping

import com.example.cinescope.domain.MediaType
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.domain.searches.SearchContent

data class ContentAPIDto(
    val page: Int,
    val results: List<Results>,
    val total_results: Int,
    val total_pages: Int
)

data class Results(
    val poster_path: String,
    val id: Int,
    val title: String,
    val media_type: String,
    val popularity: Int
)

fun ContentAPIDto.toContent(): List<MediaContent> {
    val contentList = mutableListOf<MediaContent>()

    this.results.forEach {
        /*val content = MediaContent(
            id = it.id,
            title = it.title,
            imgPath = it.poster_path,
            mediaType = MediaType.fromString(it.media_type)
        )*/
        contentList.add(MediaContent(
            id = it.id,
            title = it.title,
            imgPath = it.poster_path,
            mediaType = MediaType.fromString(it.media_type)
        ))
    }

    return contentList
}
