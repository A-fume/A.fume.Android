package com.afume.afume_android.data.remote.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AfumeServiceImpl {
    private const val BASE_URL = "http://"

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(NetworkInterceptor())
            .addNetworkInterceptor(NetworkInterceptor()).build()

    private val retrofit : Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : AfumeService = retrofit.create(AfumeService::class.java)
}