package com.example.cinescope.account.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.utils.viewModelInit

class SignUpActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: SignUpScreenViewModel by viewModels {
        viewModelInit{
            SignUpScreenViewModel(dependencies.userRepo, dependencies.userServices)
        }
    }
    companion object {
        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            SignUpScreen(
                navController = dependencies.navController,
                loading = viewModel.loading,
                error = viewModel.error,
                signedIn = viewModel.signedIn,
                onError = { viewModel.clearError() },
                onSignUpRequest = { user ->
                    viewModel.signUp(user.name, user.email, user.password)
                },
                onSignInSuccessful = { finish() },
                onSearchRequest = { SearchActivity.navigate(this)}
            )
        }
    }
}