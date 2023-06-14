package com.example.cinescope.trending.ui

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
import com.example.cinescope.trending.TrendingScreenState
import com.example.cinescope.ui.AlertError
import com.example.cinescope.ui.GridRow

@Composable
fun SeriesTab(
    state: TrendingScreenState,
    onError: () -> Unit,
    onGetSeriesDetails: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!state.loading){
            if(!state.popSeries.isNullOrEmpty()){
                for(i in 0 until state.popSeries.size step 3){
                    val series1 = state.popSeries[i]
                    val series2 = if (i + 1 < state.popSeries.size) state.popSeries[i+1] else null
                    val series3 = if (i + 2 < state.popSeries.size) state.popSeries[i+2] else null
                    GridRow(content1 = series1, content2 = series2, content3 = series3, onGetSeriesDetails)
                }
            }else{
                Text(text = "Cannot Render Series")
            }
        } else {
            Text(text = "Loading...")
        }
        if(state.error != null){
            AlertError(error = state.error, onError)
        }
    }
}