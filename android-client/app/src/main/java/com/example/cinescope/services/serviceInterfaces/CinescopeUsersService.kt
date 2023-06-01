package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.domain.user.Token
import com.example.cinescope.domain.user.UserInfo

interface CinescopeUsersService {

    suspend fun createUser(email: String, pwd: String): Token

    suspend fun login(email: String, pwd: String): Token

    suspend fun getUserInfo(token: String): UserInfo

    suspend fun deleteUser(userId: Int)

}