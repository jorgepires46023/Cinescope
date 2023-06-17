package com.example.cinescope.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun InputPasswordText(input: String, label: String, placeholder: String, updateInput:(String) -> Unit) {
    OutlinedTextField(
        value = input,
        onValueChange = { updateInput(it) },
        label = { Text(text = label) },
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        )
    )
}