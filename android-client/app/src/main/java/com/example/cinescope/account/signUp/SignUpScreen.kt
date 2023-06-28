package com.example.cinescope.account.signUp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.cinescope.domain.user.UserInput
import com.example.cinescope.domain.user.validateUserOrNull
import com.example.cinescope.ui.errors.AlertError
import com.example.cinescope.ui.inputs.InputPasswordText
import com.example.cinescope.ui.inputs.InputText
import com.example.cinescope.ui.topbar.TopBar
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.theme.CinescopeTheme

@Composable
fun SignUpScreen(
    navController: NavController,
    onSignUpRequest: (UserInput) -> Unit = {},
    onError: () -> Unit = {},
    loading: Boolean,
    error: String?,
    signedIn: Boolean,
    onSignInSuccessful: () -> Unit = {},
    onSearchRequest: () -> Unit
) {
    val spacerHeight = 16.dp
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = onSearchRequest
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
                var name by rememberSaveable{ mutableStateOf("") }
                var email by rememberSaveable{ mutableStateOf("") }
                var password by rememberSaveable{ mutableStateOf("") }

                val userInfo = validateUserOrNull(name, email, password)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(spacerHeight))
                    Column {
                        InputText(
                            input = name,
                            label = "Name",
                            placeholder = "Name",
                            updateInput = { name = it }
                        )
                    }
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
                        if(userInfo != null)
                            onSignUpRequest(userInfo)
                    },
                        enabled = !loading //TODO maybe this can change after validating email and password
                    ) {
                        Text(text = "Sign Up")//TODO refactor this after translation
                    }
                }
                if(signedIn) onSignInSuccessful()
                if(error != null) AlertError(error = error, dismiss = onError)
            }
        }
    }
}