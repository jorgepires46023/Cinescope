package com.example.cinescope.series.seriesDetails.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.SeriesState
import com.example.cinescope.series.seriesDetails.SeriesDetailsState
import com.example.cinescope.series.seriesDetails.SeriesUserData
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.cards.DescriptionCard
import com.example.cinescope.ui.dropdown.Dropdown
import com.example.cinescope.ui.images.ContentPoster

@Composable
fun SeriesDetailsTab(
    onError: () -> Unit,
    seriesDetails: SeriesDetailsState,
    userData: SeriesUserData,
    loggedIn: Boolean
) {
    if (!seriesDetails.loading) {
        if (seriesDetails.series != null) {
            Title(title = seriesDetails.series.seriesDetails.name)
            ContentPoster(
                imgPath = seriesDetails.series.seriesDetails.imgPath,
                height = 448.dp
            )
            DescriptionCard(desc = seriesDetails.series.seriesDetails.description)
            if(loggedIn){
                val currentState = if (userData.seriesData != null)
                    userData.seriesData.state ?:  SeriesState.NO_STATE.state
                else
                    SeriesState.NO_STATE.state
                Dropdown(context = "Series State", currentState, SeriesState.getStates(), userData.onChangeState)
            }
        } else {
            Text(text = "Loading...")
        }
    }
}