package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.domain.User
import com.example.cinescope.service.CinescopeProfileService

class ProfileService: CinescopeProfileService {
    override suspend fun signUp(email: String, pwd: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, pwd: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserInfo(userId: Int): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: Int) {
        TODO("Not yet implemented")
    }
}