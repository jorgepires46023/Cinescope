package com.example.cinescope.services.mockdata

import com.example.cinescope.domain.user.User
import com.example.cinescope.domain.user.UserInfo

/** Simple Data **/

const val nameRuiBorders = "Rui Borders"
const val emailRuiBorders = "ruiborders@slb.pt"
const val pwdRuiBorders = "meuPedrinhoMusaicos1904"
const val tokenRuiBorders = "1904-02-28"

/** Responses to enqueue in MockServer **/

val createUserResponse = User(tokenRuiBorders)

val loginResponse = User(tokenRuiBorders)

val getUserInfoResponse = UserInfo(emailRuiBorders, pwdRuiBorders)

/** Expected results from SearchService methods **/

val expectedCreateUserResponse = User(tokenRuiBorders)

val expectedLoginResponse = User(tokenRuiBorders)

val expectedGetUserInfoResponse = UserInfo(emailRuiBorders, pwdRuiBorders)
