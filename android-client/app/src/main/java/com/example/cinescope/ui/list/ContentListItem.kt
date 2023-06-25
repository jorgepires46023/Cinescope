package com.example.cinescope.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ContentListItem(
    onClick: () -> Unit,
    onDeleteList: () -> Unit,
    listName: String
) {
    ListItem(
        headlineContent = { Text(text = listName) },
        trailingContent = {
            IconButton(
                onClick = { onDeleteList() }
            ){
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = Color.Red,
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.clickable { onClick() }
    )
    Divider()
}