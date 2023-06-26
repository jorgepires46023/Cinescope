package com.example.cinescope.series.series.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinescope.series.series.SeriesScreenState
import com.example.cinescope.ui.errors.AlertError
import com.example.cinescope.ui.grid.SeriesDataGrid

@Composable
fun SeriesWatchingTab(
    state: SeriesScreenState,
    onError: () -> Unit,
    onGetDetails: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!state.loading){
            if(!state.watchingSeries.isNullOrEmpty()){
                SeriesDataGrid(list = state.watchingSeries, onGetDetails = onGetDetails)
            }else{
                Text(text = "You don't have Watching Series")
            }
        } else {
            Text(text = "Loading...")
        }
        if(state.error != null){
            AlertError(error = state.error, onError)
        }
    }
}