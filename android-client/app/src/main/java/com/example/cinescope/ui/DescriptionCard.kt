package com.example.cinescope.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionCard(desc: String) {
    Card(
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = desc,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )
    }
}