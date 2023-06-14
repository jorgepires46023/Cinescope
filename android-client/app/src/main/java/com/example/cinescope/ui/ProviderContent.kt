package com.example.cinescope.ui

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
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.searches.ProviderInfo

@Composable
fun ProviderContent(providers: List<ProviderInfo>) {
    val smallPadding = 4.dp
    val logoSize = 48.dp
    Column {
        providers.map { provider ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(smallPadding)
                    .fillMaxWidth()
            ) {
                Card(modifier = Modifier.height(logoSize).width(logoSize)) {
                    ImageUrl(path = provider.logoPath)
                }
                Text(
                    text = provider.providerName,
                    modifier = Modifier.padding(start = smallPadding)
                )
            }
        }
    }

}