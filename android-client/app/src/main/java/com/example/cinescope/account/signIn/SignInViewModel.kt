package com.example.cinescope.account.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.user.UserInfoRepository
import com.example.cinescope.services.cinescopeAPI.UserServices
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInViewModel(
    private val userInfoRepository: UserInfoRepository,
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
                userInfoRepository.cookie = userServices.login(email, pwd).cookie
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