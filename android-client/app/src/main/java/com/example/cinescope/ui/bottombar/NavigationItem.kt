package com.example.cinescope.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(val icon: ImageVector, val label: String)

//TODO: change Icons and labels to strings to be translated
private val moviesItem = NavigationItem(Icons.Default.Favorite, "Movies")
private val seriesItem = NavigationItem(Icons.Default.Email, "Series")
private val trendingItem = NavigationItem(Icons.Default.Star, "Trending")
private val listsItem = NavigationItem(Icons.Default.List, "Lists")
private val profileItem = NavigationItem(Icons.Default.Person, "Profile")

fun getNavItems(): List<NavigationItem> = listOf(moviesItem, seriesItem, trendingItem, listsItem, profileItem)