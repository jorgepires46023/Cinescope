package com.example.cinescope.services.mockdata

import com.example.cinescope.domain.user.Token
import com.example.cinescope.domain.user.UserInfo

/** Simple Data **/

const val emailRuiBorders = "ruiborders@slb.pt"
const val pwdRuiBorders = "meuPedrinhoMusaicos1904"
const val tokenRuiBorders = "1904-02-28"

/** Responses to enqueue in MockServer **/

val createUserResponse = Token(tokenRuiBorders)

val loginResponse = Token(tokenRuiBorders)

val getUserInfoResponse = UserInfo(emailRuiBorders, pwdRuiBorders)

/** Expected results from SearchService methods **/

val expectedCreateUserResponse = Token(tokenRuiBorders)

val expectedLoginResponse = Token(tokenRuiBorders)

val expectedGetUserInfoResponse = UserInfo(emailRuiBorders, pwdRuiBorders)
