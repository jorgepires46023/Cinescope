package com.example.cinescope.ui.grid

import androidx.compose.runtime.Composable
import com.example.cinescope.domain.searches.MediaContent

@Composable
fun MediaContentGrid(
    list: List<MediaContent>,
    onGetDetails: (Int) -> Unit
) {
    for(i in list.indices step 3){
        val content1 = list[i]
        val content2 = if ((i + 1) < list.size) list[i+1] else null
        val content3 = if ((i + 2) < list.size) list[i+2] else null
        MediaContentRow(content1 = content1, content2 = content2, content3 = content3, onGetDetails)
    }
}