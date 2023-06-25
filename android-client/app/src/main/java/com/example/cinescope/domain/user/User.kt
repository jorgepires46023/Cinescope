package com.example.cinescope.domain.user

import okhttp3.Cookie

data class User(val cookie: Cookie, val email: String, val name: String)