package com.example.cinescope.account.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.user.TokenRepository
import com.example.cinescope.services.cinescopeAPI.UserServices
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInScreenViewModel(
    private val tokenRepository: TokenRepository,
    private val userServices: UserServices
): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var signedIn by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)

    fun signIn(email: String, pwd: String){
        viewModelScope.launch {
            try {
                loading = true
                tokenRepository.userToken = userServices.login(email, pwd)
                signedIn = true
            } catch(e: Exception){
                error = e.message
            } finally {
              loading = false
            }
        }
    }

    fun clearError(){
        error = null
    }
}