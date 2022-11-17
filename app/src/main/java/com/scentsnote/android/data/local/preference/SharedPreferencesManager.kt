package com.scentsnote.android.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.CallSuper
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.scentsnote.android.ScentsNoteApplication

class SharedPreferencesManager(context: Context){

    val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var userIdx : Int
        get() = sharedPreferences.getInt(USER_IDX, 0)
        set(value) = sharedPreferences.putInt(USER_IDX, value)

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

    var userAge: Int
        get() = sharedPreferences.getInt(USER_AGE, 0)
        set(value) = sharedPreferences.putInt(USER_AGE, value)

    var accessToken: String
        get() = sharedPreferences.getString(AUTH_TOKEN)
        set(value) = sharedPreferences.putString(AUTH_TOKEN, value)

    var refreshToken: String
        get() = sharedPreferences.getString(REFRESH_TOKEN)
        set(value) = sharedPreferences.putString(REFRESH_TOKEN, value)

    var userSurvey : Boolean
        get() = sharedPreferences.getBoolean(USER_SURVEY, false)
        set(value) = sharedPreferences.putBoolean(USER_SURVEY, value)

    @CallSuper
    fun clear() {
        sharedPreferences.edit()
            .remove(USER_IDX)
            .remove(USER_EMAIL)
            .remove(USER_PASSWORD)
            .remove(USER_NICKNAME)
            .remove(USER_GENDER)
            .remove(USER_AGE)
            .remove(AUTH_TOKEN)
            .remove(REFRESH_TOKEN)
            .putBoolean(USER_SURVEY, false)
            .apply()
    }

    fun haveToken(): Boolean {
        return ScentsNoteApplication.prefManager.accessToken != ""
    }

    companion object {
        const val PREFERENCES_KEY = "preferences"
        private const val USER_IDX = "user_idx"
        private const val USER_EMAIL = "user_email"
        private const val USER_PASSWORD = "user_password"
        private const val USER_NICKNAME = "user_nickname"
        private const val USER_GENDER = "user_gender"
        private const val USER_AGE = "user_age"
        private const val AUTH_TOKEN = "authentication_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val USER_SURVEY = "user_survey"

        private fun SharedPreferences.getString(key: String) =
            getString(key, "").orEmpty()

        private fun SharedPreferences.putString(key: String, value: String) =
            edit().putString(key, value).apply()

        private fun SharedPreferences.putInt(key: String, value: Int) =
            edit().putInt(key, value).apply()

        private fun SharedPreferences.putBoolean(key: String, value: Boolean) =
            edit().putBoolean(key, value).apply()
    }
}