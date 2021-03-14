package com.afume.afume_android.data.remote.network

import com.afume.afume_android.data.vo.request.RequestLogin
import com.afume.afume_android.data.vo.request.RequestRegister
import com.afume.afume_android.data.vo.request.RequestSurvey
import com.afume.afume_android.data.vo.response.*
import retrofit2.http.*

interface AfumeService {
    //Sign - validate email
    @GET("user/validate/email")
    suspend fun getValidateEmail(
        @Query("email") email : String
    ): ResponseBase<Boolean>

    //Sign - validate nickname
    @GET("user/validate/name")
    suspend fun getValidateNickname(
        @Query("nickname") nickname : String
    ): ResponseBase<Boolean>

    //Sign - sign up
    @POST("user/register")
    suspend fun postRegisterInfo(
        @Body body: RequestRegister
    ): ResponseBase<Boolean>

    //Sign - sign in
    @POST("user/login")
    suspend fun postLoginInfo(
        @Body body: RequestLogin
    ): ResponseBase<ResponseLogin>

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