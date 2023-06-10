package com.example.cinescope.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AlertError(error: String, dismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { dismiss() },
        text = { Text(text = error) },
        confirmButton = {
            Button(onClick = { dismiss() }) {
                Text(text = "OK")
            }
        }
    )
}