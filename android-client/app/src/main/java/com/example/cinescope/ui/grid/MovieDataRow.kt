package com.example.cinescope.ui.grid

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.ui.DeleteDialog
import com.example.cinescope.ui.images.ImageUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDataRow(
    content1: MovieData,
    content2: MovieData?,
    content3: MovieData?,
    navigate: (id: Int) -> Unit,
    onListsScope: Boolean,
    onDeleteFromList: (Int) -> Unit
) {
    var dialog by rememberSaveable{ mutableStateOf(false) }
    var movieId by rememberSaveable{ mutableIntStateOf(-1) }
    val scope = rememberCoroutineScope()
    var isLongPressStarted by remember { mutableStateOf(false) }

    val cardHeight = 168.dp
    val cardWidth = 112.dp
    //TODO arrange content id's
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            shape = CardDefaults.outlinedShape,
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
                .size(width = cardWidth, height = cardHeight)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            isLongPressStarted = true
                            scope.launch {
                                if (isLongPressStarted && onListsScope) {
                                    dialog = true
                                    movieId = content1.tmdbId
                                }
                            }
                        },
                        onPress = {
                            isLongPressStarted = false
                        },
                        onTap = { navigate(content1.tmdbId) }
                    )
                }
        ) {
            ImageUrl(path = content1.imgPath, )
        }
        if(content2 != null){
            Card(
                shape = CardDefaults.outlinedShape,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .size(width = cardWidth, height = cardHeight)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                isLongPressStarted = true
                                scope.launch {
                                    if (isLongPressStarted && onListsScope) {
                                        dialog = true
                                        movieId = content2.tmdbId
                                    }
                                }
                            },
                            onPress = {
                                isLongPressStarted = false
                            },
                            onTap = { navigate(content2.tmdbId) }
                        )
                    }
            ) {
                ImageUrl(path = content2.imgPath)
            }
        }
        if(content3 != null){
            Card(
                shape = CardDefaults.outlinedShape,
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .size(width = cardWidth, height = cardHeight)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                isLongPressStarted = true
                                scope.launch {
                                    if (isLongPressStarted && onListsScope) {
                                        dialog = true
                                        movieId = content3.tmdbId
                                    }
                                }
                            },
                            onPress = {
                                isLongPressStarted = false
                            },
                            onTap = { navigate(content3.tmdbId) }
                        )
                    }
            ) {
                ImageUrl(path = content3.imgPath)
            }
        }
        if(dialog && movieId != -1){
            DeleteDialog(
                onDismiss = { dialog = false },
                onDelete = {
                    onDeleteFromList(movieId)
                    movieId = -1
                    dialog = false
                }
            )
        }
    }
}

