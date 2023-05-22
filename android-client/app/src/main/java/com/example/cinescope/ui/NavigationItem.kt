package com.example.cinescope.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(val icon: ImageVector, val label: String)

val moviesItem = NavigationItem(Icons.Default.Favorite, "Movies")
val seriesItem = NavigationItem(Icons.Default.Email, "Series")
val trendingItem = NavigationItem(Icons.Default.Star, "Trending")
val listsItem = NavigationItem(Icons.Default.List, "Lists")
val profileItem = NavigationItem(Icons.Default.Person, "Profile")

fun getNavItems(): List<NavigationItem> = listOf(moviesItem, seriesItem, trendingItem, listsItem, profileItem)