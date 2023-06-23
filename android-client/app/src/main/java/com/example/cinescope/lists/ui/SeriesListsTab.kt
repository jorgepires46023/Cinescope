package com.example.cinescope.lists.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.ui.list.ContentListItem
import com.example.cinescope.ui.inputs.CreateListDialog
import com.example.cinescope.ui.list.CreateListItem

@Composable
fun SeriesListsTab(
    onCreateList: (String) -> Unit,
    seriesLists: List<ContentList>?,
    onUpdateSeriesLists: () -> Unit,
    onGetListDetails: (Int) -> Unit
) {
    var dialog by rememberSaveable{ mutableStateOf(false) }
    if(!seriesLists.isNullOrEmpty()){
        for(list in seriesLists){
            ContentListItem(
                listName = list.name,
                onClick = { onGetListDetails(list.id) }
            )
        }
    }
    CreateListItem(
        onOpenDialog = { dialog = true }
    )
    if(dialog){
        CreateListDialog(
            onCreateList = { name ->
                dialog = false
                onCreateList(name)
                onUpdateSeriesLists()
            },
            dismiss = { dialog = false }
        )
    }
}