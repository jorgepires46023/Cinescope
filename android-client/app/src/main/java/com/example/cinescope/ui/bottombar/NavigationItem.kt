package com.example.cinescope.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.getDrawable
import com.example.cinescope.R
import com.example.cinescope.lists.ListsActivity
import com.example.cinescope.movies.movies.MoviesActivity
import com.example.cinescope.profile.ProfileActivity
import com.example.cinescope.series.series.SeriesActivity
import com.example.cinescope.trending.TrendingActivity
data class NavigationItem(val icon: ImageVector, val label: String, val clazz: Class<*>?)// TODO make clazz non-nullable

//TODO: change Icons and labels to strings to be translated
private val moviesItem = NavigationItem(Icons.Default.Movie, "Movies", MoviesActivity::class.java)
private val seriesItem = NavigationItem(Icons.Default.Tv, "Series", SeriesActivity::class.java)
private val trendingItem = NavigationItem(Icons.Default.Whatshot, "Trending", TrendingActivity::class.java)
private val listsItem = NavigationItem(Icons.Default.List, "Lists", ListsActivity::class.java)
private val profileItem = NavigationItem(Icons.Default.Person, "Profile", ProfileActivity::class.java)

private val navItems = listOf(moviesItem, seriesItem, trendingItem, listsItem, profileItem)

const val TRENDING_IDX = 2
val selectedItem = mutableStateOf(navItems[TRENDING_IDX])

fun getNavItems(): List<NavigationItem> = navItems