package com.example.cinescope.ui.providers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinescope.domain.searches.PT
import com.example.cinescope.ui.cards.CardTitle

@Composable
fun WatchProviders(providers: PT?) {
    val smallPadding = 4.dp
    Card(
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .padding(smallPadding)
            .fillMaxWidth()
    ) {
        Column {
            if (providers == null) {
                Row(

                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Not Available in Portugal",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            } else {
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
}