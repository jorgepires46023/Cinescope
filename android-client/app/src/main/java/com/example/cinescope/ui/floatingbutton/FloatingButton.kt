package com.example.cinescope.ui.floatingbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.cinescope.ui.AddContentDialog

@Composable
fun FloatingButton(

) {
    var dialog by rememberSaveable { mutableStateOf(false) }

    SmallFloatingActionButton(
        onClick = { dialog = true },
        modifier = Modifier.alpha(0.6f)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null )
    }

    if (dialog) {
        AddContentDialog(
            contentText = "Hello World",
            onDismiss = { dialog = false }
        )
    }
}