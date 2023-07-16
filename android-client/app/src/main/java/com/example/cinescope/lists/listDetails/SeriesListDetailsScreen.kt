package com.example.cinescope.lists.listDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.content.SeriesListDetails
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.errors.AlertError
import com.example.cinescope.ui.grid.SeriesDataGrid
import com.example.cinescope.ui.topbar.TopBar

@Composable
fun SeriesListDetailsScreen(
    list: SeriesListDetails?,
    onGetDetails: (Int) -> Unit,
    onDeleteFromList: (Int) -> Unit,
    onSearchRequest: () -> Unit,
    navController: NavController,
    error: String?,
    onError: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                onSearchRequested = onSearchRequest
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController
            )
        }
    ) { innerPadding ->

        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if(list != null){
                    if(list.results.isNotEmpty()){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                        ) {
                            Title(title = list.info.name)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        SeriesDataGrid(
                            list = list.results,
                            onGetDetails = onGetDetails,
                            onListsScope = true,
                            onDeleteFromList = onDeleteFromList
                        )
                    } else {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Empty List",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    if(error != null) AlertError(error = error, dismiss = onError)
                }
            }
        }

    }
}