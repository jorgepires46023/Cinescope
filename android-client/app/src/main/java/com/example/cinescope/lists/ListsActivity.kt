package com.example.cinescope.lists

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinescope.DependenciesContainer
import com.example.cinescope.account.signUp.SignUpActivity
import com.example.cinescope.account.signUp.SignUpScreen
import com.example.cinescope.account.signUp.SignUpScreenViewModel
import com.example.cinescope.utils.viewModelInit

class ListsActivity: ComponentActivity() {
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: ListsViewModel by viewModels {
        viewModelInit{
            ListsViewModel(dependencies.moviesServices, dependencies.seriesServices)
        }
    }
    companion object {
        fun navigate(origin: Context){
            with(origin){
                val intent = Intent(this, ListsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        setContent{
            ListsScreen(
                navController = dependencies.navController
            )
        }
    }
}