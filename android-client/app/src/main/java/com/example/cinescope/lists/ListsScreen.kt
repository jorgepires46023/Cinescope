package com.example.cinescope.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.lists.ui.ListsTabs
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme

@Composable
fun ListsScreen(
    navController: NavController,
    onCreateMovieList: (String) -> Unit,
    moviesLists: List<ContentList>?,
    onUpdateMoviesLists: () -> Unit,
    onCreateSeriesList: (String) -> Unit,
    seriesLists: List<ContentList>?,
    onUpdateSeriesLists: () -> Unit,
    onChangeScreen: (Int, Int) -> Unit
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = null
                )
            },
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ){ innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ListsTabs(
                        onCreateMovieList = onCreateMovieList,
                        moviesLists = moviesLists,
                        onUpdateMoviesLists = onUpdateMoviesLists,
                        onCreateSeriesList = onCreateSeriesList,
                        seriesLists = seriesLists,
                        onUpdateSeriesLists = onUpdateSeriesLists,
                        onChangeScreen = onChangeScreen
                    )
                }
            }
        }
    }
}