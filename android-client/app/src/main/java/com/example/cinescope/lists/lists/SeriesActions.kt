package com.example.cinescope.lists.lists

import com.example.cinescope.domain.content.ContentList

data class SeriesActions(
    val onCreateSeriesList: (String) -> Unit,
    val seriesLists: List<ContentList>?,
    val onUpdateSeriesLists: () -> Unit,
    val deleteSeriesList: (Int) -> Unit
)