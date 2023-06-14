package com.example.cinescope.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ContentPoster(imgPath: String, height: Dp) {
    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
    ) {
        ImageUrl(path = imgPath)
    }
}