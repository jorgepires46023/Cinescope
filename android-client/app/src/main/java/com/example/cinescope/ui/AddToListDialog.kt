package com.example.cinescope.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.ui.list.ListItemCheckbox

@Composable
fun AddToListDialog(
    contentText: String,
    onDismiss: () -> Unit,
    lists: List<ContentList>?,
    onAddToList: (Int) -> Unit,
    onDeleteFromList: (Int) -> Unit,
    userData: UserDataContent?,
    onUpdate: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = contentText)},
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "OK")
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(240.dp).verticalScroll(rememberScrollState())

            ) {
                if (lists != null) {
                    lists.forEach { list ->
                        ListItemCheckbox(
                            name = list.name,
                            onAdd = { onAddToList(list.id) },
                            onDelete = { onDeleteFromList(list.id) },
                            checked = userData?.lists != null && userData.lists.contains(list),
                        )
                    }
                } else
                    Text(text = "No available lists")
            }
        }
    )
}