package com.example.cinescope.domain.user

data class UserCredentials(val email: String, val password: String)

fun validateCredentialsOrNull(email: String?, password: String?): UserCredentials?{
    if(email.isNullOrBlank() || password.isNullOrBlank()) return null
    return UserCredentials(email, password)
}