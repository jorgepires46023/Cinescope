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
fun MoviesTab(state: TrendingScreenState, onError: () -> Unit, onGetMovieDetails: (id: Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!state.loading){
            if(!state.popMovies.isNullOrEmpty()){
                for(i in 0 until state.popMovies.size step 3){
                    val movie1 = state.popMovies[i]
                    val movie2 = if ((i + 1) < state.popMovies.size) state.popMovies[i+1] else null
                    val movie3 = if ((i + 2) < state.popMovies.size) state.popMovies[i+2] else null
                    GridRow(content1 = movie1, content2 = movie2, content3 = movie3, onGetMovieDetails)
                }
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