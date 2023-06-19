package com.example.cinescope.utils

import okhttp3.Cookie
import okhttp3.HttpUrl

private const val dummyScheme = "http"
private const val dummyDomain = "example.com"

fun cookieParser(string: String): Cookie?{
    val httpUrl = HttpUrl.Builder()
        .scheme(dummyScheme)
        .host(dummyDomain)
        .build()
    return Cookie.parse(httpUrl, string)
}