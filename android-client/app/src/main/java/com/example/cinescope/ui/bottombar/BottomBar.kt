package com.example.cinescope.ui.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun BottomBar(
    navController: NavController,
){
    val items = getNavItems()

    NavigationBar(
        containerColor = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach {item ->
            NavigationBarItem(
                selected = selectedItem.value == item.clazz,
                icon = { Icon(imageVector = item.icon, contentDescription = null)},
                label = { Text(text = item.label) },
                enabled = selectedItem.value != item.clazz,
                onClick = {
                    selectedItem.value = item.clazz
                    navController.navigate(item.clazz)
                }
            )
        }
    }
}