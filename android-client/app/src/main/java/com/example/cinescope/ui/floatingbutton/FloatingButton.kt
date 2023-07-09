package com.example.cinescope.ui.floatingbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.ui.AddToListDialog

@Composable
fun FloatingButton(
    lists: List<ContentList>?,
    onGetLists: () -> Unit,
    onAddToList: (Int) -> Unit,
    onDeleteFromList: (Int) -> Unit,
    userData: UserDataContent?
) {
    var dialog by rememberSaveable { mutableStateOf(false) }

    SmallFloatingActionButton(
        onClick = {
            dialog = true
            onGetLists()
        },
        modifier = Modifier.alpha(0.6f)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null )
    }

    if (dialog) {
        AddToListDialog(
            contentText = "Add to List",
            onDismiss = { dialog = false },
            lists = lists,
            onAddToList = onAddToList,
            onDeleteFromList = onDeleteFromList,
            userData = userData
        )
    }
}