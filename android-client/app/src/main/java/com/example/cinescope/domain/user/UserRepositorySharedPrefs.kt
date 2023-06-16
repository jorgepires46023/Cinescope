package com.example.cinescope.domain.user

import android.content.Context

class UserRepositorySharedPrefs(private val context: Context): TokenRepository {
    private val userTokenKey = "token"
    private val userInfoPrefs = "userPrefs"

    private val prefs by lazy {
        context.getSharedPreferences(userInfoPrefs, Context.MODE_PRIVATE)
    }

    override var userToken: Token?
        get() {
            val savedToken = prefs.getString(userTokenKey, null)
            return if(savedToken != null)
                Token(savedToken)
            else null
        }

        set(value) {
            if(value == null)
                prefs.edit()
                    .remove(userTokenKey)
            else
                prefs.edit()
                    .putString(userTokenKey, value.token)
        }
}