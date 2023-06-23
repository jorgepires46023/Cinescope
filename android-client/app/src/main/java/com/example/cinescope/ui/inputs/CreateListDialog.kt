package com.example.cinescope.ui.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CreateListDialog(
    onCreateList: (String) -> Unit = {}, //TODO remove this default {}
    dismiss: () -> Unit
) {
    var name by rememberSaveable{ mutableStateOf("") }
    Dialog(dismiss) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    Text(text = "CreateList")
                    Spacer(Modifier.size(16.dp))
                    InputText(
                        input = name,
                        label = "Name",
                        placeholder = "Name",
                        updateInput = { name = it })
                }
                Spacer(Modifier.size(4.dp))
                Row(
                    Modifier.padding(8.dp).fillMaxWidth(),
                    Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    TextButton(
                        onClick = { dismiss() },
                        content = { Text("CANCEL") },
                    )
                    TextButton(
                        onClick = { onCreateList(name) },
                        content = { Text("CREATE") },
                    )
                }
            }
        }
    }
}