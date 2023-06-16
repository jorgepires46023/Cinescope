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


@Composable
fun BottomBar(
    onSearchRequested: (()-> Unit)? = null, //on Search icon touched will trigger this function
    navController: NavController
){
    val items = getNavItems()

    NavigationBar(
        containerColor = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach {item ->
            NavigationBarItem(
                selected = selectedItem.value == item,
                icon = { Icon(imageVector = item.icon, contentDescription = null)},
                label = { Text(text = item.label) },
                enabled = selectedItem.value != item,
                onClick = {
                    selectedItem.value = item
                    item.clazz?.let { navController.navigate(it) }//TODO not delete while has null classes
                }
            )
        }
    }
}