@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cinescope.ui.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinescope.R

//TODO -> Check ExperimentalMaterial3Api import and how this affects our project
@Composable
fun TopBar(
    title: String = stringResource(id = R.string.app_name),
    onSearchRequested: (()-> Unit)? = null //on Search icon touched will trigger this function
){
    TopAppBar(
        colors = topAppBarColors(
        Color.LightGray
        ),
        modifier = Modifier.fillMaxWidth(),
        title = { Text(
            text = title,
            textAlign = TextAlign.Center
        )},
        actions = {
            if(onSearchRequested != null){
                Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.CenterEnd){
                    IconButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = onSearchRequested //TODO -> check if this should be hardcoded because this will always do the same action
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Feature not available yet")
                    }
                }

            }
        },
    )
}