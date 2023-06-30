package com.example.cinescope.ui.list

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun ListItemCheckbox(
    name: String,
    onAdd: () -> Unit,
    onDelete: () -> Unit,
    checkWatchedEp: Boolean,
    onClick: () -> Unit = {},
) {
val checkState = remember { mutableStateOf(checkWatchedEp) }

    ListItem(
        headlineContent = { Text(text = name) },
        trailingContent = {
            Checkbox(
                checked =  checkState.value,
                onCheckedChange = {
                    checkState.value = if (it) {
                        onDelete()
                        false
                    }else{
                        onAdd()
                        true
                    }
                }
            )
        }
    )
    Divider()
}
