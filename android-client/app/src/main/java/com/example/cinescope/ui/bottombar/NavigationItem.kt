package com.example.cinescope.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cinescope.series.series.SeriesActivity
import com.example.cinescope.trending.TrendingActivity
data class NavigationItem(val icon: ImageVector, val label: String, val clazz: Class<*>?)

//TODO: change Icons and labels to strings to be translated
private val moviesItem = NavigationItem(Icons.Default.Favorite, "Movies", null)
private val seriesItem = NavigationItem(Icons.Default.Email, "Series", SeriesActivity::class.java)
private val trendingItem = NavigationItem(Icons.Default.Star, "Trending", TrendingActivity::class.java)
private val listsItem = NavigationItem(Icons.Default.List, "Lists", null)
private val profileItem = NavigationItem(Icons.Default.Person, "Profile", null)

private val navItems = listOf(moviesItem, seriesItem, trendingItem, listsItem, profileItem)

const val TRENDING_IDX = 2
val selectedItem = mutableStateOf(navItems[TRENDING_IDX])

fun getNavItems(): List<NavigationItem> = navItems