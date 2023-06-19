package com.example.cinescope.profile



import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.account.signIn.SignInActivity
import com.example.cinescope.account.signUp.SignUpActivity
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

        val loggedIn = dependencies.tokenRepo.user != null


        setContent {
            Toast.makeText(this, "LoggedIn: $loggedIn", Toast.LENGTH_SHORT).show()
            ProfileScreen(
                loggedIn = loggedIn,
                error = viewModel.error,
                onError = { viewModel.clearError() },
                navController = dependencies.navController,
                onLoginRequest = { SignInActivity.navigate(this) },
                onLogoutRequest = {
                    dependencies.tokenRepo.user = null
                    startActivity(intent)
                },
                onSignUpRequest = { SignUpActivity.navigate(this) }
            )
        }
    }
}