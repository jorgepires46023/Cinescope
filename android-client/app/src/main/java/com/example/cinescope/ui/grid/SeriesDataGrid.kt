package com.example.cinescope.ui.grid

import androidx.compose.runtime.Composable
import com.example.cinescope.domain.content.SeriesData

@Composable
fun SeriesDataGrid(
    list: List<SeriesData>,
    onGetDetails: (Int) -> Unit
) {
    for(i in list.indices step 3){
        val content1 = list[i]
        val content2 = if ((i + 1) < list.size) list[i+1] else null
        val content3 = if ((i + 2) < list.size) list[i+2] else null
        SeriesDataRow(content1 = content1, content2 = content2, content3 = content3, onGetDetails)
    }
}