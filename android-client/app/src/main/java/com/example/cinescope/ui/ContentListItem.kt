package com.example.cinescope.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ContentListItem(
    onClick: () -> Unit = {}, //TODO remove this default function
    listName: String
) {
    ListItem(
        headlineContent = { Text(text = listName) },
        trailingContent = {
            IconButton(
                onClick = { onClick }
            ){
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null
                )
            }
        }
    )
    Divider()
}