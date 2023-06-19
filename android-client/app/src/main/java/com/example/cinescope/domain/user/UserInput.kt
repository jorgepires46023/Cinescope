package com.example.cinescope.domain.user

data class UserInput(val name: String, val email: String, val password: String)

fun validateUserOrNull(name: String?, email: String?, password: String?): UserInput?{
    if(email.isNullOrBlank() || password.isNullOrBlank() || name.isNullOrBlank() ) return null
    return UserInput(name, email, password)
}