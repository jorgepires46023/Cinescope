package com.example.cinescope.profile

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.account.signIn.SignInActivity
import com.example.cinescope.account.signUp.SignUpActivity
import com.example.cinescope.search.SearchActivity
import com.example.cinescope.utils.viewModelInit

class ProfileActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: ProfileScreenViewModel by viewModels {
        viewModelInit {
            ProfileScreenViewModel(dependencies.userServices)
        }
    }

    override fun onResume() {
        super.onResume()

        val user = dependencies.userRepo.user
        if(user != null){
            viewModel.getUserInfo(user.cookie.value) //TODO this method needs to change on API
        }
        val loggedIn = user != null

        setContent {
            ProfileScreen(
                loggedIn = loggedIn,
                error = viewModel.error,
                onError = { viewModel.clearError() },
                navController = dependencies.navController,
                onLoginRequest = { SignInActivity.navigate(this) },
                onLogoutRequest = {
                    dependencies.userRepo.user = null
                    startActivity(intent)
                },
                onSignUpRequest = { SignUpActivity.navigate(this) },
                user = user,//TODO Just to display something on profile, this should depend on viewModel
                onSearchRequested = { SearchActivity.navigate(this)}
            )
        }
    }
}