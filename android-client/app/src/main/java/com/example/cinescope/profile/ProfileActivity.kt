package com.example.cinescope.profile

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
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

        setContent {

        }
    }
}