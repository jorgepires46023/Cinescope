package com.example.cinescope.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.domain.searches.Movie
import com.example.cinescope.domain.searches.Series
import com.example.cinescope.movies.movieDetails.MovieDetailsActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCard(content1: MediaContent, content2: MediaContent?, content3: MediaContent?, navigate: (id: Int) -> Unit) {
    val cardHeight = 168.dp
    val cardWidth = 112.dp
    //TODO arrange content id's
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            onClick = { navigate(content1.id) },
            shape = CardDefaults.outlinedShape,
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
                .size(width = cardWidth, height = cardHeight)
        ) {
            ImageUrl(path = content1.imgPath)
        }
        if(content2 != null){
            Card(
                onClick = { navigate(content2.id) },
                shape = CardDefaults.outlinedShape,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .size(width = cardWidth, height = cardHeight)
            ) {
                ImageUrl(path = content2.imgPath)
            }
        }
        if(content3 != null){
            Card(
                onClick = { navigate(content3.id) },
                shape = CardDefaults.outlinedShape,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .size(width = cardWidth, height = cardHeight)
            ) {
                ImageUrl(path = content3.imgPath)
            }
        }
    }
}