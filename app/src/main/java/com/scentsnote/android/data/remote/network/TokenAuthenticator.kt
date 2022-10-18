package com.scentsnote.android.data.remote.network

import androidx.annotation.Nullable
import com.google.gson.JsonObject
import com.scentsnote.android.data.local.preference.SharedPreferencesManager
import com.scentsnote.android.data.repository.SignRepository
import com.scentsnote.android.data.vo.request.RequestLogin
import com.scentsnote.android.data.vo.response.ResponseLogin
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException


class TokenAuthenticator(private val prefManager: SharedPreferencesManager) : Authenticator {
    private val signRepository = SignRepository()

    @Nullable
    override fun authenticate(route: Route?, response: Response): Request? {
        try {
            val loginInfo = RequestLogin(prefManager.userEmail,prefManager.userPassword)
            val tokenResponse: ResponseLogin =
            runBlocking {
                signRepository.postLoginInfo(loginInfo)
            }

            tokenResponse.let {
                prefManager.accessToken = it.token
                prefManager.refreshToken = it.refreshToken
            }

            return getNewRequest(response.request(), prefManager.accessToken)

        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    onSessionExpiration()
                    return null
                }
            }
        }
        return null
    }

    private fun getNewRequest(request: Request, accessToken: String): Request? {
        return request.newBuilder()
            .removeHeader("x-access-token")
            .header("x-access-token", accessToken)
            .build()
    }

    private fun onSessionExpiration() {
        prefManager.clear()
    }
}