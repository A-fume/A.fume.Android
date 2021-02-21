package com.afume.afume_android.data.remote.network

import com.afume.afume_android.data.vo.response.ResponseBase
import com.afume.afume_android.data.vo.response.ResponseSeries
import retrofit2.http.GET

interface AfumeService {

//    @GET("/")
//    fun getNewPerfumeList(
//        @Body body : RequestData
//    ): Observable<ResponseData>

    @GET("series")
    suspend fun getSeries(
    ): ResponseBase<ResponseSeries>

}