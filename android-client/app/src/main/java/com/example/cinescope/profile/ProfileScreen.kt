package com.example.cinescope.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme


@Composable
fun ProfileScreen(
    loggedIn: Boolean,
    error: String?,
    onError: () -> Unit,
    onSearchRequested: () -> Unit = {},
    navController: NavController
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = onSearchRequested
                )
            },
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ){
                innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            )
        }
    }
}
