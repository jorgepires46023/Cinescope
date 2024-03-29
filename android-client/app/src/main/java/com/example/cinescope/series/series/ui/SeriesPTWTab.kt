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
fun SeriesPTWTab(
    state: SeriesScreenState,
    error: String?,
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
            if(!state.ptwSeries.isNullOrEmpty()){
                SeriesDataGrid(list = state.ptwSeries, onGetDetails = onGetDetails)
            }else{
                Text(text = "You don't have Plan To Watch Series")
            }
        } else {
            Text(text = "Loading...")
        }
        if(error != null){
            AlertError(error = error, dismiss = onError)
        }
    }
}