package com.example.cinescope.account.signIn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinescope.domain.user.UserCredentials
import com.example.cinescope.domain.user.UserInfo
import com.example.cinescope.domain.user.validateCredentialsOrNull
import com.example.cinescope.ui.AlertError
import com.example.cinescope.ui.InputPasswordText
import com.example.cinescope.ui.InputText
import com.example.cinescope.ui.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme
@Composable
fun SignInScreen(
    navController: NavController,
    onSignInRequest: (UserCredentials) -> Unit = {}, //TODO delete this default {}?
    onError: () -> Unit = {},
    loading: Boolean,
    error: String?,
    signedIn: Boolean,
    onSignInSuccessful: () -> Unit = {}
) {
    val spacerHeight = 16.dp
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = null
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
            ){
                var email by rememberSaveable{ mutableStateOf("") }
                var password by rememberSaveable{ mutableStateOf("") }

                val credentials = validateCredentialsOrNull(email, password)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(spacerHeight))
                    Column {
                        InputText(
                            input = email,
                            label = "Email",
                            placeholder = "example@email.com",
                            updateInput = { email = it }
                        )
                    }
                    Spacer(modifier = Modifier.height(spacerHeight))
                    Column {
                        InputPasswordText(
                            input = password,
                            label = "Password",
                            placeholder = "password",
                            updateInput = { password = it }
                        )
                    }
                    Spacer(modifier = Modifier.height(spacerHeight))
                    Button(onClick = {
                        if(credentials != null)
                            onSignInRequest(credentials)
                        },
                        enabled = !loading //TODO maybe this can change after validating email and password
                    ) {
                        Text(text = "Login")//TODO refactor this after translation
                    }
                }
                if(signedIn) onSignInSuccessful()
                if(error != null) AlertError(error = error, dismiss = onError)
            }
        }
    }
}
