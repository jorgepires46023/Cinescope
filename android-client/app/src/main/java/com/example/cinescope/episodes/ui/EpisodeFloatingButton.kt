package com.example.cinescope.episodes.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.example.cinescope.ui.AddToListDialog
import com.example.cinescope.ui.DeleteDialog

@Composable
fun EpisodeFloatingButton(
    onAddWatched: () -> Unit,
    onDeleteWatched: () -> Unit,
    isWatched: Boolean,
) {
    var checkedState by remember { mutableStateOf(isWatched) }
    var dialog by rememberSaveable { mutableStateOf(false) }
    SmallFloatingActionButton(
        onClick = {
            if(checkedState)
                dialog = true
            else {
                onAddWatched()
                checkedState = true
            }
        },
        modifier = Modifier.alpha(0.6f)
    ){
        if(!checkedState)
            Icon(imageVector = Icons.Default.Add, contentDescription = null )
        else
            Icon(imageVector = Icons.Default.Check, contentDescription = null )
    }

    if (dialog) {
        DeleteDialog(
            onDismiss = { dialog = false },
            onDelete = {
                onDeleteWatched()
                checkedState = false
                dialog = false
            },
            message = "Are you sure you want to remove this episode from your watchlist?"
        )
    }
}