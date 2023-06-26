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
data class ListsTabsInfo(val title: String, val onUpdate: () -> Unit)
@Composable
fun ListsTabs(
    onError: () -> Unit = { },
    movieActions: MovieActions,
    seriesActions: SeriesActions,
    onChangeScreen: (Int, Int) -> Unit
) {
    var tabIdx by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        ListsTabsInfo("Movies", movieActions.onUpdateMoviesLists),
        ListsTabsInfo("Series", seriesActions.onUpdateSeriesLists)
    ) //TODO insert this strings in xml for translations

    TabRow(selectedTabIndex = tabIdx) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = tabIdx == index,
                onClick = {
                    tabIdx = index
                    tab.onUpdate()
                },
                text = { Text(tab.title) }
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