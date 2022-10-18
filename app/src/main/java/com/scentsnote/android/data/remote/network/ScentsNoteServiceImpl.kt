package com.scentsnote.android.data.remote.network

import com.scentsnote.android.ScentsNoteApplication.Companion.prefManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ScentsNoteServiceImpl {
    private const val TIMEOUT_LIMIT: Long = 10
    private const val BASE_URL = "http://13.124.104.53:8082/A.fume/api/0.0.1/"

    private var client: OkHttpClient = OkHttpClient().newBuilder().apply {
        authenticator(
            TokenAuthenticator(
                prefManager
            )
        )
        addNetworkInterceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
                header("x-access-token", prefManager.accessToken)
            }.build())
        }
        connectTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(client)
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    val service : ScentsNoteService = retrofit.create(ScentsNoteService::class.java)
}