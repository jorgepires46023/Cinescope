package com.example.cinescope.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cinescope.domain.user.UserInfo
import com.example.cinescope.ui.Title
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.errors.AlertError
import com.example.cinescope.ui.theme.CinescopeTheme
import com.example.cinescope.ui.topbar.TopBar

data class NotLoggedInActions(
    val onLoginRequest: () -> Unit = {},
    val onSignUpRequest: () -> Unit = {}
)

data class LoggedInState(
    val onLogoutRequest: () -> Unit = {},
    val user: UserInfo?
)

@Composable
fun ProfileScreen(
    loggedIn: Boolean,
    loading: Boolean,
    error: String?,
    onError: () -> Unit,
    onSearchRequested: () -> Unit,
    notLoggedInActions: NotLoggedInActions,
    loggedInState: LoggedInState,
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
        ){ innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ){
                if(!loggedIn){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            Button(onClick = { notLoggedInActions.onLoginRequest() }) {
                                Text(text = "Login")
                            }
                        }
                        Row {
                            Button(onClick = { notLoggedInActions.onSignUpRequest() }) {
                                Text(text = "Create Account")
                            }
                        }
                    }
                }else{
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        if(loggedInState.user != null) {
                            Row {
                                Title(title = loggedInState.user.name)
                            }
                        }
                        Button(
                            onClick = {
                                loggedInState.onLogoutRequest()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {
                            Text(text = "Logout")
                        }
                    }
                }
                if(error != null) AlertError(error = error, dismiss = onError)
            }
        }
    }
}
