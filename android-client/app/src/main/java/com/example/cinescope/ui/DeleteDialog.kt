package com.example.cinescope.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    message: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(text = message) },
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