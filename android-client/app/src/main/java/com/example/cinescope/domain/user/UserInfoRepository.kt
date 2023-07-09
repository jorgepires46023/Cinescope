package com.example.cinescope.domain.user

import okhttp3.Cookie

interface UserInfoRepository {
    var cookie: Cookie?
}