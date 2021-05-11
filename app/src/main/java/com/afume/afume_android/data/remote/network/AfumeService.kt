package com.afume.afume_android.data.remote.network

import com.afume.afume_android.data.vo.request.RequestSurvey
import com.afume.afume_android.data.vo.response.ResponseBase
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.data.vo.response.ResponsePerfume
import com.afume.afume_android.data.vo.response.ResponseSeries
import retrofit2.http.*

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
    ):ResponseBase<ResponseKeyword>

    @POST("user/survey")
    suspend fun postSurvey(
        @Header("x-access-token") token : String,
        @Body body: RequestSurvey
    ):ResponseBase<Int>

    @GET("user/{userIdx}/perfume/liked")
    suspend fun getLikedPerfume(
        @Header("x-access-token") token : String,
        @Path("userIdx") userIdx : Int
    ):ResponseBase<ResponsePerfume>
}