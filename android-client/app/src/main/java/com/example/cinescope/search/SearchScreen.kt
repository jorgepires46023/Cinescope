package com.example.cinescope.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.MediaType
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.domain.searches.SearchContent
import com.example.cinescope.ui.errors.AlertError
import com.example.cinescope.ui.grid.MediaContentGrid
import com.example.cinescope.ui.theme.CinescopeTheme
import com.example.cinescope.ui.topbar.SearchTopBar

@Composable
fun SearchScreen(
    onBackRequest: () -> Unit,
    onSearch: (String) -> Unit,
    results: List<MediaContent>?,
    loading: Boolean,
    error: String?,
    onError: () -> Unit,
    onGetDetails: (Int, MediaType) -> Unit
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SearchTopBar(onBackRequest = onBackRequest, onSearch = onSearch )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                if (!loading) {
                    if (results != null) {
                        Column (
                            modifier = Modifier.verticalScroll(rememberScrollState())
                                .padding(top = 8.dp)
                        ){
                            MediaContentGrid(
                                list = results,
                                onGetDetails = onGetDetails
                            )
                        }
                    }
                } else {
                    Text(text = "Loading....")
                }
                if(error != null) AlertError(error = error, dismiss = onError)
            }
        }
    }
}