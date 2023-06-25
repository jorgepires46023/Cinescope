package com.example.cinescope.account.signIn

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.utils.viewModelInit

class SignInActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: SignInScreenViewModel by viewModels {
        viewModelInit{
            SignInScreenViewModel(dependencies.userRepo, dependencies.userServices)
        }
    }
    companion object {
        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        setContent{
            SignInScreen(
                navController = dependencies.navController,
                onError = { viewModel.clearError() },
                loading = viewModel.loading,
                error = viewModel.error,
                onSignInRequest = { credentials ->
                    viewModel.signIn(credentials.email, credentials.password)
                },
                signedIn = viewModel.signedIn,
                onSignInSuccessful = { finish() }
            )
        }
    }
}