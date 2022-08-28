package com.scentsnote.android.data.remote.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ScentsNoteServiceImpl {
    private const val BASE_URL = "http://13.124.104.53:8082/A.fume/api/0.0.1/"

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

    val service : ScentsNoteService = retrofit.create(ScentsNoteService::class.java)
}