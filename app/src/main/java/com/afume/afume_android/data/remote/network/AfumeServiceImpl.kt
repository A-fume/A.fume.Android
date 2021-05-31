package com.afume.afume_android.data.remote.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AfumeServiceImpl {
    private const val BASE_URL = "http://3.35.246.117:3001/A.fume/api/0.0.1/"

//    private val okHttpClient: OkHttpClient =
//        OkHttpClient.Builder().addInterceptor(NetworkInterceptor())
//            .addNetworkInterceptor(NetworkInterceptor()).build()

    private val retrofit : Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
//        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : AfumeService = retrofit.create(AfumeService::class.java)
}