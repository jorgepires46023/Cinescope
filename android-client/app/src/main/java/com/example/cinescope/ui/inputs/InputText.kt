package com.example.cinescope.ui.inputs

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun InputText(input: String, label: String, placeholder: String, updateInput:(String) -> Unit) {
    OutlinedTextField(
        value = input,
        onValueChange = { updateInput(it) },
        label = { Text(text = label) },
        singleLine = true,
        placeholder = { Text(text = placeholder) }
    )
}

