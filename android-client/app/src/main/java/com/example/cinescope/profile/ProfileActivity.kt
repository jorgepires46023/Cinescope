package com.example.cinescope.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.account.signIn.SignInActivity
import com.example.cinescope.account.signUp.SignUpActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.utils.viewModelInit

class ProfileActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: ProfileViewModel by viewModels {
        viewModelInit {
            ProfileViewModel(dependencies.userServices)
        }
    }

    companion object {
        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cookie = dependencies.userRepo.cookie
        if(cookie != null){
            viewModel.getUserInfo(cookie)
        }
        viewModel.setLoggedInValue( cookie != null )

        setContent {
            ProfileScreen(
                loggedIn = viewModel.signedIn,
                loading = viewModel.loading,
                error = viewModel.error,
                onError = { viewModel.clearError() },
                navController = dependencies.navController,
                notLoggedInActions = NotLoggedInActions(
                    onLoginRequest = {
                        SignInActivity.navigate(this)
                        finish()
                    },
                    onSignUpRequest = {
                        SignUpActivity.navigate(this)
                        finish()
                    }
                ),
                loggedInState = LoggedInState(
                    onLogoutRequest = {
                        dependencies.userRepo.cookie = null
                        startActivity(intent)
                    },
                    user = viewModel.userInfo
                ),
                onSearchRequested = { SearchActivity.navigate(this)}
            )
        }
    }
}