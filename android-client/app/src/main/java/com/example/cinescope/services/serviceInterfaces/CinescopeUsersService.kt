package com.example.cinescope.services.serviceInterfaces

import com.example.cinescope.services.dtos.UserInfo
import com.example.cinescope.services.dtos.UserToken

interface CinescopeUsersService {

    suspend fun createUser(email: String, pwd: String): UserToken

    suspend fun login(email: String, pwd: String): UserToken

    suspend fun getUserInfo(token: String): UserInfo

    suspend fun deleteUser(userId: Int)

}