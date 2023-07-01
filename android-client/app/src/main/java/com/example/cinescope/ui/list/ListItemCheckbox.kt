package com.example.cinescope.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ListItemCheckbox(
    name: String,
    onAdd: () -> Unit,
    onDelete: () -> Unit,
    checkWatchedEp: Boolean,
    onClick: () -> Unit = {},
) {
    /*


    ListItem(
        headlineContent = { Text(text = name) },
        trailingContent = {
            Checkbox(
                checked =  checkWatchedEp,
                onCheckedChange = {
                    checkWatchedEp = if (checkWatchedEp) {
                        onDelete()
                        false
                    }else{
                        onAdd()
                        true
                    }
                }
            )
        },
        modifier = Modifier.clickable { onClick() }
    )
    Divider()

     */
}
