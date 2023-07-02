package com.example.cinescope.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
) {
    var checkState by rememberSaveable { mutableStateOf(checked) }
    ListItem(
        headlineContent = { Text(text = name) },
        trailingContent = {
            Checkbox(
                checked =  checked,
                onCheckedChange = {
                    checkState = if (checked) {
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


}
