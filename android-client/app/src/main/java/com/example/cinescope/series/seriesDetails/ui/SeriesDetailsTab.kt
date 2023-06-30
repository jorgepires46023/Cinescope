package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.SeriesState
import com.example.cinescope.series.seriesDetails.SeriesDetailsState
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.cards.DescriptionCard
import com.example.cinescope.ui.dropdown.Dropdown
import com.example.cinescope.ui.images.ContentPoster

@Composable
fun SeriesDetailsTab(
    onError: () -> Unit,
    onGetDetails: (Int) -> Unit,
    state: SeriesDetailsState,
    loggedIn: Boolean,
    onChangeState: (String) -> Unit
) {
    if (!state.loading) {
        if (state.series != null) {
            Title(title = state.series.seriesDetails.name)
            ContentPoster(
                imgPath = state.series.seriesDetails.imgPath,
                height = 448.dp
            )
            DescriptionCard(desc = state.series.seriesDetails.description)
            if(loggedIn){
                val currentState = if (state.seriesData != null)
                    state.seriesData.state ?:  SeriesState.NO_STATE.state
                else
                    SeriesState.NO_STATE.state
                Dropdown(context = "Series State", currentState, SeriesState.getStates(), onChangeState)
            }
        } else {
            Text(text = "Cannot Render Series Details")
        }
    } else {
        Text(text = "Loading...")
    }
}