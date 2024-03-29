package com.example.cinescope.lists.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.content.SeriesListDetails
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.topbar.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.grid.SeriesDataGrid
import com.example.cinescope.ui.theme.CinescopeTheme

@Composable
fun SeriesListScreen(
    navController: NavController,
    seriesList: SeriesListDetails?,
    onGetSeriesDetails: (Int) -> Unit,
    onBackRequest: () -> Unit,
    onDeleteFromList: (Int) -> Unit,
    onSearchRequest: () -> Unit
) {
    CinescopeTheme {
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
        ){ innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { onBackRequest() }) {
                            Text(text = "Back")
                        }
                    }
                    if(seriesList != null){
                        if(seriesList.results.isNotEmpty()){
                            Title(title = seriesList.info.name)
                            Spacer(modifier = Modifier.height(8.dp))
                            SeriesDataGrid(
                                list = seriesList.results,
                                onGetDetails = onGetSeriesDetails,
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
                    }
                }
            }

        }
    }
}