package com.example.cinescope.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinescope.domain.user.UserInfo
import com.example.cinescope.services.cinescopeAPI.UserServices
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val userServices: UserServices
): ViewModel() {

    var loading by mutableStateOf(false)
        private set

    var userInfo by mutableStateOf<UserInfo?>(null)
        private set

    var error by mutableStateOf<String?>(null)

    fun getUserInfo(token: String){
        viewModelScope.launch {
            try {
                loading = true
                userInfo = userServices.getUserInfo(token)
            }catch(e: Exception){
                error = e.message
            }finally {
                loading = false
            }
        }
    }
    fun clearError(){
        error = null
    }
}