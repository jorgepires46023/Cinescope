package com.example.cinescope.service

import com.example.cinescope.domain.User

interface CinescopeProfileService {

    suspend fun signUp(email: String, pwd: String): User

    suspend fun signIn(email: String, pwd: String): User

    suspend fun getUserInfo(userId: Int): User

    suspend fun deleteUser(userId: Int)

}