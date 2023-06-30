package com.example.cinescope.lists.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.lists.SeriesActions
import com.example.cinescope.ui.DeleteDialog
import com.example.cinescope.ui.list.ContentListItem
import com.example.cinescope.ui.inputs.CreateListDialog
import com.example.cinescope.ui.list.CreateListItem

@Composable
fun SeriesListsTab(
    seriesActions: SeriesActions,
    onGetListDetails: (Int) -> Unit
) {
    var dialog by rememberSaveable{ mutableStateOf(false) }
    var deleteListDialog by rememberSaveable{ mutableStateOf(false) }
    var listId by rememberSaveable{ mutableIntStateOf(-1) }

    if(!seriesActions.seriesLists.isNullOrEmpty()){
        for(list in seriesActions.seriesLists){
            ContentListItem(
                listName = list.name,
                onClick = { onGetListDetails(list.id) },
                onDeleteList = {
                    deleteListDialog = true
                    listId = list.id
                }
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
                seriesActions.onCreateSeriesList(name)
                seriesActions.onUpdateSeriesLists()
            },
            dismiss = { dialog = false }
        )
    }
    if(deleteListDialog && listId != -1){
        DeleteDialog(
            onDismiss = { deleteListDialog = false },
            onDelete = {
                seriesActions.deleteSeriesList(listId)
                seriesActions.onUpdateSeriesLists()
                deleteListDialog = false
            },
            message = "Do you want to delete ${seriesActions.seriesLists?.find { it.id == listId }?.name} list?"
        )
    }
}