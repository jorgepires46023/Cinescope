package com.example.cinescope.ui.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinescope.search.ui.SearchInput
import com.example.cinescope.ui.inputs.InputText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    onBackRequest: () -> Unit,
    onSearch: (String) -> Unit
) {
    var query by rememberSaveable{ mutableStateOf("") }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            Color.LightGray
        ),
        modifier = Modifier.fillMaxWidth(),
        title = {
            SearchInput(
                input = query,
                placeholder = "Search...",
                updateInput = { query = it },
                onSearch = { onSearch(query) }
             )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackRequest() }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Feature not available yet")
            }
        },
        actions = {
            Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.CenterEnd){
                IconButton(
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onSearch(query) }
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Feature not available yet")
                }
            }
        },
    )
}