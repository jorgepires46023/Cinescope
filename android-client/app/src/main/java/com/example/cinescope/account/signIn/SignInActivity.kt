package com.example.cinescope.account.signIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.profile.ProfileActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.utils.viewModelInit

class SignInActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: SignInViewModel by viewModels {
        viewModelInit{
            SignInViewModel(dependencies.userRepo, dependencies.userServices)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                onSignInSuccessful = {
                    ProfileActivity.navigate(this)
                    finish()
                },
                onSearchRequest = {SearchActivity.navigate(this)}
            )
        }
    }
}