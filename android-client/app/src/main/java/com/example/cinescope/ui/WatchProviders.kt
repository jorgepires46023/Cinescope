package com.example.cinescope.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.searches.PT

@Composable
fun WatchProviders(providers: PT) {
    val smallPadding = 4.dp
    Card(
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .padding(smallPadding)
            .fillMaxWidth()
    ) {
        Column {
            Row(modifier = Modifier.padding(smallPadding)) {
                CardTitle(title = "Streaming Services")//TODO translation
            }
            if (providers.flatrate != null) {
                ProviderContent(providers = providers.flatrate)
            }
            Row(modifier = Modifier.padding(smallPadding)) {
                CardTitle(title = "Buy Services")//TODO translation
            }
            if (providers.buy != null) {
                ProviderContent(providers = providers.buy)
            }
            Row(modifier = Modifier.padding(smallPadding)) {
                CardTitle(title = "Renting Services")//TODO translation
            }
            if (providers.rent != null) {
                ProviderContent(providers = providers.rent)
            }
        }
    }
}