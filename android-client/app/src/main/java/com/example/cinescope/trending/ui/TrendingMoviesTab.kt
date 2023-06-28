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
import com.example.cinescope.domain.MediaType
import com.example.cinescope.trending.TrendingScreenState
import com.example.cinescope.ui.errors.AlertError
import com.example.cinescope.ui.grid.MediaContentGrid

@Composable
fun TrendingMoviesTab(state: TrendingScreenState, onError: () -> Unit, onGetMovieDetails: (Int, MediaType) -> Unit) {
    //TODO check if we should pass state or only the lists
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!state.loading){
            if(!state.popMovies.isNullOrEmpty()){
                MediaContentGrid(list = state.popMovies, onGetDetails = onGetMovieDetails)
            }else{
                Text(text = "Cannot Render Movies")
            }
        } else {
            Text(text = "Loading...")
        }
        if(state.error != null){
            AlertError(error = state.error, onError)
        }
    }
}