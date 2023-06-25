package com.example.cinescope.lists.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.lists.MovieActions
import com.example.cinescope.ui.DeleteDialog
import com.example.cinescope.ui.list.ContentListItem
import com.example.cinescope.ui.inputs.CreateListDialog
import com.example.cinescope.ui.list.CreateListItem

@Composable
fun MoviesListsTab(
    movieActions: MovieActions,
    onGetListDetails: (Int) -> Unit
) {
    var dialog by rememberSaveable{ mutableStateOf(false) }
    var deleteListDialog by rememberSaveable{ mutableStateOf(false) }
    var listId by rememberSaveable{ mutableIntStateOf(-1) }

    if(!movieActions.moviesLists.isNullOrEmpty()){
        for(list in movieActions.moviesLists){
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
                movieActions.onCreateMovieList(name)
                movieActions.onUpdateMoviesLists()
            },
            dismiss = { dialog = false }
        )
    }
    if(deleteListDialog && listId != -1){
        DeleteDialog(
            onDismiss = { deleteListDialog = false },
            onDelete = {
                movieActions.deleteMovieList(listId)
                movieActions.onUpdateMoviesLists()
                deleteListDialog = false
            }
        )
    }
}