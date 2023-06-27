package com.example.cinescope.ui.dropdown

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.MovieState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    context: String,
    currentState: String,
    states: List<String>,
    changeState: (String) -> Unit
) {
    var expandable by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(currentState) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = context)
        Button(
            onClick = { expandable = !expandable },
            shape = RectangleShape
        ) {
            Text(text = selectedItem)
            Spacer(modifier = Modifier.width(8.dp))
            if(!expandable)
                Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
            else
                Icon(imageVector = Icons.Default.ExpandLess, contentDescription = null)
        }
        DropdownMenu(
            expanded = expandable,
            onDismissRequest = { expandable = false },
            modifier = Modifier.focusable(false)
        ) {
            states.forEach { state ->
                DropdownMenuItem(
                    text = { Text(text = state) },
                    onClick = {
                        selectedItem = state
                        expandable = false
                        changeState(selectedItem)
                    }
                )
            }
        }
    }
}