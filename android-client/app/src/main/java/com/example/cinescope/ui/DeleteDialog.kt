package com.example.cinescope.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    val deleteMsg = "Do you want to delete this Item?"
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(text = deleteMsg) },
        confirmButton = {
            Button(onClick = { onDelete() }) {
                Text(text = "OK")
            }
        }, dismissButton = {
            Button(onClick = { onDismiss() } ) {
                Text(text = "CANCEL")
            }
        }
    )
}