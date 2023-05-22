package com.example.cinescope.trending

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class TrendingActivity: ComponentActivity() {
    override fun onResume() {
        super.onResume()

        setContent {
            TrendingScreen()
        }
    }
}