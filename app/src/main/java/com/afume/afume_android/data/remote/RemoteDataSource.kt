package com.afume.afume_android.data.remote

import com.afume.afume_android.data.vo.request.RequestSurvey
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.data.vo.response.SeriesInfo

interface RemoteDataSource {
    suspend fun getValidateEmail(email : String): Boolean
    suspend fun getValidateNickname(nickname : String): Boolean
    suspend fun getSeries(): MutableList<SeriesInfo>
    suspend fun getSurveyPerfume(token: String): MutableList<PerfumeInfo>
    suspend fun getKeyword(): MutableList<ResponseKeyword>
    suspend fun postSurvey(token: String, body: RequestSurvey): String
    suspend fun getLikedPerfume(token : String, userIdx : Int): MutableList<PerfumeInfo>
}