package com.afume.afume_android.data.remote.network

import com.afume.afume_android.data.vo.response.ResponseBase
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.data.vo.response.ResponsePerfume
import com.afume.afume_android.data.vo.response.ResponseSeries
import retrofit2.http.GET
import retrofit2.http.Header

interface AfumeService {

//    @GET("/")
//    fun getNewPerfumeList(
//        @Body body : RequestData
//    ): Observable<ResponseData>

    //Survey - series
    @GET("series")
    suspend fun getSeries(
    ): ResponseBase<ResponseSeries>

    //Survey - perfume
    @GET("perfume/survey")
    suspend fun getSurveyPerfume(
        @Header("x-access-token") token : String
    ):ResponseBase<ResponsePerfume>

    //Survey - keyword
    @GET("keyword")
    suspend fun getKeyword(
    ):ResponseBase<MutableList<ResponseKeyword>>

}