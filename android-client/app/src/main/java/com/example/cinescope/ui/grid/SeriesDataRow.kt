package com.example.cinescope.ui.grid

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.ui.DeleteDialog
import com.example.cinescope.ui.images.ImageUrl
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesDataRow(
    content1: SeriesData,
    content2: SeriesData?,
    content3: SeriesData?,
    navigate: (id: Int) -> Unit,
    onListsScope: Boolean,
    onDeleteFromList: (Int) -> Unit = {}
) {
    var dialog by rememberSaveable{ mutableStateOf(false) }
    var seriesId by rememberSaveable{ mutableIntStateOf(-1) }
    var seriesName by rememberSaveable{ mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    var isLongPressStarted by remember { mutableStateOf(false) }

    val cardHeight = 168.dp
    val cardWidth = 112.dp
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
                                    seriesId = content1.tmdbId
                                    seriesName = content1.name
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
            ImageUrl(path = content1.imgPath)
            if(onListsScope){
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Delete")
                }
            }
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
                                        seriesId = content2.tmdbId
                                        seriesName = content2.name
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
                                        seriesId = content3.tmdbId
                                        seriesName = content3.name
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
        if(dialog && seriesName != null && seriesId != -1){
            DeleteDialog(
                onDismiss = { dialog = false },
                onDelete = {
                    onDeleteFromList(seriesId)
                    seriesId = -1
                    seriesName = null
                    dialog = false
                },
                message = "Do you want to delete $seriesName from list?"
            )
        }
    }
}