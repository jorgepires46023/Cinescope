package com.example.cinescope.account.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.user.UserInfoRepository
import com.example.cinescope.services.cinescopeAPI.UserServices
import kotlinx.coroutines.launch
import java.lang.Exception

class SignUpScreenViewModel(
    private val userInfoRepository: UserInfoRepository,
    private val userServices: UserServices
): ViewModel() {
    var loading by mutableStateOf(false)
        private set

    var signedIn by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)

    fun signUp(name: String, email: String, pwd: String){
        viewModelScope.launch {
            try {
                loading = true
                userInfoRepository.user = userServices.createUser(name, email, pwd)
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