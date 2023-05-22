package com.example.cinescope.trending

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinescope.ui.BottomBar
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.theme.CinescopeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    onSearchRequested: () -> Unit = { }
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = onSearchRequested
                )
            },
            bottomBar = {
                BottomBar(
                    onSearchRequested = onSearchRequested
                )
            }
        ){
            innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun TrendingScreenPreview() {
    CinescopeTheme {
        TrendingScreen()
    }
}