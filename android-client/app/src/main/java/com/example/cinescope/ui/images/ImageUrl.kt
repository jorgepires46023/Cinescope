package com.example.cinescope.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.cinescope.R

const val IMG_DOMAIN = "https://image.tmdb.org/t/p/original"

@Composable
fun ImageUrl(path: String?) {
    if (path != null){
        AsyncImage(
            model = IMG_DOMAIN + path,
            contentDescription = "null",
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.TopCenter
        )
    }
    else
        Image(painter = painterResource(id = R.drawable.no_image), contentDescription = null)
}