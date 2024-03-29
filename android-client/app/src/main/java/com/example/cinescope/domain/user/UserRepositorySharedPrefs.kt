package com.example.cinescope.domain.user

import android.content.Context
import com.google.gson.Gson
import okhttp3.Cookie

class UserRepositorySharedPrefs(
    private val context: Context,
    private val gson: Gson
    ): UserInfoRepository {
    private val userTokenKey = "token"
    private val userInfoPrefs = "userPrefs"

    private val prefs by lazy {
        context.getSharedPreferences(userInfoPrefs, Context.MODE_PRIVATE)
    }

    override var cookie: Cookie?
        get() {
            val authCookie = gson.fromJson(prefs.getString(userTokenKey, null), Cookie::class.java) ?: return null
            if(authCookie.expiresAt < System.currentTimeMillis()){
                cookie = null
                return null
            }
            return authCookie
        }

        set(value) {
            if(value == null)
                prefs.edit()
                    .remove(userTokenKey)
                    .apply()
            else
                prefs.edit()
                    .putString(userTokenKey, gson.toJson(value))
                    .apply()
        }
}