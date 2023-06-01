package com.example.cinescope.domain.content

data class UserDataContent(
    val id: Int,
    val state: String,
    val results: List<ContentList>
)