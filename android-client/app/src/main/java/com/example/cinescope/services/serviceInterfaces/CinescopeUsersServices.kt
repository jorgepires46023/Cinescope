package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.user.User
import com.example.cinescope.domain.user.UserInfo
import okhttp3.Cookie

interface CinescopeUsersServices {

    suspend fun createUser(name: String, email: String, pwd: String): User

    suspend fun login(email: String, pwd: String): User

    suspend fun getUserInfo(cookie: Cookie): UserInfo
}