package com.example.cinescope.ui.grid

import android.content.res.loader.ResourcesLoader
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cinescope.R
import com.example.cinescope.domain.MediaType
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.ui.images.ImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaContentRow(content1: MediaContent, content2: MediaContent?, content3: MediaContent?, navigate: (id: Int, mediaType: MediaType) -> Unit) {
    val cardHeight = 168.dp
    val cardWidth = 112.dp
    //TODO arrange content id's
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            onClick = { navigate(content1.id, content1.mediaType) },
            shape = CardDefaults.outlinedShape,
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
                .size(width = cardWidth, height = cardHeight)
        ) {
            if (content1.imgPath != null) {
                ImageUrl(path = content1.imgPath)
            } else {
                Image(painter = painterResource(id = R.drawable.no_image), contentDescription = null)
            }
        }
        if(content2 != null){
            Card(
                onClick = { navigate(content2.id, content1.mediaType) },
                shape = CardDefaults.outlinedShape,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .size(width = cardWidth, height = cardHeight)
            ) {
                if (content2.imgPath != null) {
                    ImageUrl(path = content2.imgPath)
                } else {
                    Image(painter = painterResource(id = R.drawable.no_image), contentDescription = null)
                }
            }
        }
        if(content3 != null){
            Card(
                onClick = { navigate(content3.id, content1.mediaType) },
                shape = CardDefaults.outlinedShape,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .size(width = cardWidth, height = cardHeight)
            ) {
                if (content3.imgPath != null) {
                    ImageUrl(path = content3.imgPath)
                } else {
                    Image(painter = painterResource(id = R.drawable.no_image), contentDescription = null)
                }
            }
        }
    }
}