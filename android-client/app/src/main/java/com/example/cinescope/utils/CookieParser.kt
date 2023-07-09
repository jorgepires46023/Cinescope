package com.example.cinescope.utils

import okhttp3.Cookie
import okhttp3.HttpUrl

private const val scheme = "http"
private const val domain = "example.com"

fun cookieParser(setCookie: String): Cookie?{
    val httpUrl = HttpUrl.Builder()
        .scheme(scheme)
        .host(domain)
        .build()
    return Cookie.parse(httpUrl, setCookie)
}