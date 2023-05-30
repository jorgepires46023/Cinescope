package com.example.cinescope.services.dtos

data class UserToken(val token: String)

data class UserInfo(
    val name: String,
    val token: String,
    val email: String,
    val pwd: String
)

