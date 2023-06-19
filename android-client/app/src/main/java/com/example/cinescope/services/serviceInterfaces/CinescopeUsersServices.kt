package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.user.User
import com.example.cinescope.domain.user.UserInfo

interface CinescopeUsersServices {

    suspend fun createUser(name: String, email: String, pwd: String): User

    suspend fun login(email: String, pwd: String): User

    suspend fun getUserInfo(token: String): UserInfo

    suspend fun deleteUser(userId: Int)

}