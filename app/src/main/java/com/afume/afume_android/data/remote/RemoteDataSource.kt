package com.afume.afume_android.data.remote

import com.afume.afume_android.data.vo.request.*
import com.afume.afume_android.data.vo.response.*

interface RemoteDataSource {
    suspend fun getValidateEmail(email : String): Boolean
    suspend fun getValidateNickname(nickname : String): Boolean
    suspend fun postRegisterInfo(body: RequestRegister): String
    suspend fun postLoginInfo(body: RequestLogin): ResponseLogin
    suspend fun getSeries(): MutableList<SeriesInfo>
    suspend fun getSurveyPerfume(token: String): MutableList<PerfumeInfo>
    suspend fun getKeyword(): MutableList<ResponseKeyword>
    suspend fun postSurvey(token: String, body: RequestSurvey): String
    suspend fun getLikedPerfume(token : String, userIdx : Int): MutableList<PerfumeInfo>
    suspend fun getMyPerfume(token : String, userIdx : Int): MutableList<ResponseMyPerfume>
    suspend fun putMyInfo(token: String, userIdx: Int, body: RequestEditMyInfo) : ResponseEditMyInfo
    suspend fun putPassword(token: String, body: RequestEditPassword) : String
}