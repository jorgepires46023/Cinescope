package com.example.cinescope.ui.providers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.searches.PT
import com.example.cinescope.ui.cards.CardTitle

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
            if (providers.flatrate != null) {
                Row(modifier = Modifier.padding(smallPadding)) {
                    CardTitle(title = "Streaming Services")//TODO translation
                }
                ProviderContent(providers = providers.flatrate)
            }
            if (providers.buy != null) {
                Row(modifier = Modifier.padding(smallPadding)) {
                    CardTitle(title = "Buy Services")//TODO translation
                }
                ProviderContent(providers = providers.buy)
            }
            if (providers.rent != null) {
                Row(modifier = Modifier.padding(smallPadding)) {
                    CardTitle(title = "Renting Services")//TODO translation
                }
                ProviderContent(providers = providers.rent)
            }
        }
    }
}