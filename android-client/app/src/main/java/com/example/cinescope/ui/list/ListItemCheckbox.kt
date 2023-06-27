package com.example.cinescope.ui.list

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun ListItemCheckbox(
    listName: String,
    onAddToList: () -> Unit,
    onDeleteFromList: () -> Unit,
    checked: Boolean
) {
    var checkedState by rememberSaveable { mutableStateOf(checked) }

    ListItem(
        headlineContent = { Text(text = listName) },
        trailingContent = {
            Checkbox(
                checked = checkedState ,
                onCheckedChange = {
                    if (checkedState) {
                        onDeleteFromList()
                        checkedState = false
                    }else{
                        onAddToList()
                        checkedState = true
                    }
                }
            )
        }
    )
    Divider()
}
