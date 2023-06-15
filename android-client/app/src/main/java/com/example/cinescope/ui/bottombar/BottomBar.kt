package com.example.cinescope.ui.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

const val TRENDING_IDX = 2
@Composable
fun BottomBar(
    onSearchRequested: (()-> Unit)? = null //on Search icon touched will trigger this function
){
    val items = getNavItems()
    val selectedItem = remember{ mutableStateOf(items[TRENDING_IDX]) }
    NavigationBar(
        containerColor = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach {item ->
            NavigationBarItem(
                selected = selectedItem.value == item,
                icon = { Icon(imageVector = item.icon, contentDescription = null)},
                label = { Text(text = item.label) },
                onClick = {
                    selectedItem.value = item
                    //TODO(Add navigation to another activity)
                }
            )
        }
    }
}