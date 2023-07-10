package com.example.cinescope.services.mockdata

import com.example.cinescope.domain.user.User
import com.example.cinescope.domain.user.UserInfo
import okhttp3.Cookie
import okhttp3.HttpUrl

/** Simple Data **/

const val nameRuiBorders = "Rui Borders"
const val emailRuiBorders = "ruiborders@slb.pt"
const val pwdRuiBorders = "meuPedrinhoMusaicos1904"
val cookieRuiBorders = Cookie.parse(HttpUrl.Builder().build(), "exampleCookie")

/** Responses to enqueue in MockServer **/

val createUserResponse = UserInfo(nameRuiBorders, emailRuiBorders)

val loginResponse = UserInfo(nameRuiBorders, emailRuiBorders)

val getUserInfoResponse = UserInfo(nameRuiBorders, emailRuiBorders)

/** Expected results from SearchService methods **/

val expectedCreateUserResponse = UserInfo(nameRuiBorders, emailRuiBorders)

val expectedLoginResponse = cookieRuiBorders?.let { User(it, nameRuiBorders, emailRuiBorders) }

val expectedGetUserInfoResponse = UserInfo(nameRuiBorders, emailRuiBorders)
