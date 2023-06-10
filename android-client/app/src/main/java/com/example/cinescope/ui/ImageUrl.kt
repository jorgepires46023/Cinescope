package com.example.cinescope.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

const val IMG_DOMAIN = "https://image.tmdb.org/t/p/original"

@Composable
fun ImageUrl(path: String) {
    AsyncImage(
        model = IMG_DOMAIN + path,
        contentDescription = "null",
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.TopCenter
    )
}