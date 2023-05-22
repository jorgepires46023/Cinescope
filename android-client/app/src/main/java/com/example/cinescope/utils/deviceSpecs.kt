package com.example.cinescope.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.ScreenLayout

@Composable
fun getCurrentLayout(): ScreenLayout {
    val deviceConfig = LocalConfiguration.current
    return(ScreenLayout(height = deviceConfig.screenHeightDp.dp, width = deviceConfig.screenWidthDp.dp))
}