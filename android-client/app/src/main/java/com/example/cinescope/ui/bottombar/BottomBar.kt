package com.example.cinescope.ui.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cinescope.lists.lists.ListsActivity
import com.example.cinescope.movies.movies.MoviesActivity
import com.example.cinescope.profile.ProfileActivity
import com.example.cinescope.series.series.SeriesActivity
import com.example.cinescope.trending.TrendingActivity

@Composable
fun BottomBar(
    navController: NavController
){
    NavigationBar(
        containerColor = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        navItems.forEach {item ->
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

data class NavigationItem(val icon: ImageVector, val label: String, val clazz: Class<*>)

private val moviesItem = NavigationItem(Icons.Default.Movie, "Movies", MoviesActivity::class.java)
private val seriesItem = NavigationItem(Icons.Default.Tv, "Series", SeriesActivity::class.java)
private val trendingItem = NavigationItem(Icons.Default.Whatshot, "Trending", TrendingActivity::class.java)
private val listsItem = NavigationItem(Icons.Default.List, "Lists", ListsActivity::class.java)
private val profileItem = NavigationItem(Icons.Default.Person, "Profile", ProfileActivity::class.java)

private val navItems = listOf(moviesItem, seriesItem, trendingItem, listsItem, profileItem)

const val TRENDING_IDX = 2
val selectedItem = mutableStateOf(navItems[TRENDING_IDX].clazz)