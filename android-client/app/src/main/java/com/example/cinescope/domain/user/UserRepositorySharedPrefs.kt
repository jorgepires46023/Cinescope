package com.example.cinescope.domain.user

import android.content.Context
import com.google.gson.Gson
import okhttp3.Cookie

class UserRepositorySharedPrefs(
    private val context: Context,
    private val gson: Gson
    ): TokenRepository {
    private val userTokenKey = "token"
    private val userNameKey = "name"
    private val userEmailKey = "email"
    private val userInfoPrefs = "userPrefs"

    private val prefs by lazy {
        context.getSharedPreferences(userInfoPrefs, Context.MODE_PRIVATE)
    }

    override var user: User?
        get() {
            val name = prefs.getString(userNameKey, null)
            val email = prefs.getString(userEmailKey, null)
            val userToken = gson.fromJson(prefs.getString(userTokenKey, null), Cookie::class.java)
            return if(userToken != null && name != null && email != null) User(userToken, email, name)
            else null
        }

        set(value) {
            if(value == null)
                prefs.edit()
                    .remove(userTokenKey)
                    .remove(userNameKey)
                    .remove(userEmailKey)
                    .apply()
            else
                prefs.edit()
                    .putString(userTokenKey, gson.toJson(value.token))
                    .putString(userNameKey, value.name)
                    .putString(userEmailKey, value.email)
                    .apply()
        }
}