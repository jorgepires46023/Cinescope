package com.example.cinescope.lists.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.lists.MovieActions
import com.example.cinescope.lists.SeriesActions

@Composable
fun ListsTabs(
    onError: () -> Unit = { },
    movieActions: MovieActions,
    seriesActions: SeriesActions,
    onChangeScreen: (Int, Int) -> Unit
) {
    var tabIdx by remember { mutableIntStateOf(0) }
    val tabs = listOf("Movies", "Series") //TODO insert this strings in xml for translations

    TabRow(selectedTabIndex = tabIdx) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = tabIdx == index,
                onClick = { tabIdx = index },
                text = { Text(title) }
            )
        }
    }
    when(tabIdx){
        0 -> { MoviesListsTab(
            movieActions,
            onGetListDetails = { listId -> onChangeScreen(ListsState.MovieListDetails, listId) }
        ) }
        1 -> { SeriesListsTab(
            seriesActions,
            onGetListDetails = { listId -> onChangeScreen(ListsState.SeriesListDetails, listId) }
        ) }
    }
}