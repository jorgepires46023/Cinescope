package com.example.cinescope.ui.list

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

@Composable
fun ListItemCheckbox(
    name: String,
    onAdd: () -> Unit,
    onDelete: () -> Unit,
    checked: Boolean,
    onClick: () -> Unit = {},
    onUpdate: () -> Unit
) {
    var checkedState by rememberSaveable { mutableStateOf(checked) }

    ListItem(
        headlineContent = { Text(text = name) },
        trailingContent = {
            Checkbox(
                checked =  checkedState  ,
                onCheckedChange = {
                    if (checkedState) {
                        onDelete()
                        checkedState = false
                    }else{
                        onAdd()
                        checkedState = true
                    }
                    onUpdate()
                }
            )
        }
    )
    Divider()
}
