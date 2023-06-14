package com.example.cinescope.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.searches.PT

@Composable
fun WatchProviders(providers: PT) {
    Card(
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        CardTitle(title = "Available in:")
        Column {
            Row(modifier = Modifier.padding(4.dp)) {
                Text(text = "Streaming Services")
                if (providers.flatrate != null) {
                    providers.flatrate.map { service ->
                        Card {
                            ImageUrl(path = service.logoPath)
                        }
                        Text(text = service.providerName)
                    }
                }
            }
        }
    }
}