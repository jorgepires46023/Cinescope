package com.example.cinescope.domain.user

import okhttp3.Cookie

data class User(val token: Cookie, val email: String, val name: String)