package com.afume.afume_android.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.CallSuper

class SharedPreferencesManager (context: Context){
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

    var userEmail: String
        get() = sharedPreferences.getString(USER_EMAIL)
        set(value) = sharedPreferences.putString(USER_EMAIL, value)

    var userPassword: String
        get() = sharedPreferences.getString(USER_PASSWORD)
        set(value) = sharedPreferences.putString(USER_PASSWORD, value)

    var userNickname: String
        get() = sharedPreferences.getString(USER_NICKNAME)
        set(value) = sharedPreferences.putString(USER_NICKNAME, value)

    var userGender: String
        get() = sharedPreferences.getString(USER_GENDER)
        set(value) = sharedPreferences.putString(USER_GENDER, value)

    var userAge: String
        get() = sharedPreferences.getString(USER_AGE)
        set(value) = sharedPreferences.putString(USER_AGE, value)

    var accessToken: String
        get() = sharedPreferences.getString(AUTH_TOKEN)
        set(value) = sharedPreferences.putString(AUTH_TOKEN, value)

    var refreshToken: String
        get() = sharedPreferences.getString(REFRESH_TOKEN)
        set(value) = sharedPreferences.putString(REFRESH_TOKEN, value)

    @CallSuper
    fun clear() {
        sharedPreferences.edit()
            .remove(AUTH_TOKEN)
            .remove(REFRESH_TOKEN)
            .apply()
    }

    companion object {
        const val PREFERENCES_KEY = "preferences"
        private const val USER_EMAIL = "user_email"
        private const val USER_PASSWORD = "user_password"
        private const val USER_NICKNAME = "user_nickname"
        private const val USER_GENDER = "user_gender"
        private const val USER_AGE = "user_age"
        private const val AUTH_TOKEN = "authentication_token"
        private const val REFRESH_TOKEN = "refresh_token"

        private fun SharedPreferences.getString(key: String) =
            getString(key, "").orEmpty()

        private fun SharedPreferences.putString(key: String, value: String) =
            edit().putString(key, value).apply()
    }
}