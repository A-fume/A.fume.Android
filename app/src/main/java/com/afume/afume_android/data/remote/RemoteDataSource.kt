package com.afume.afume_android.data.remote

import com.afume.afume_android.data.vo.NewPerfumeListData
import com.afume.afume_android.data.vo.request.RequestSurvey
import com.afume.afume_android.data.vo.response.KeywordInfo
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.SeriesInfo
import io.reactivex.Observable

interface RemoteDataSource {
    fun getNewPerfumeList(): Observable<NewPerfumeListData>
    suspend fun getSeries(): MutableList<SeriesInfo>
    suspend fun getSurveyPerfume(token: String): MutableList<PerfumeInfo>
    suspend fun getKeyword(): MutableList<KeywordInfo>
    suspend fun postSurvey(token: String, body: RequestSurvey): String
    suspend fun getLikedPerfume(token : String, userIdx : Int): MutableList<PerfumeInfo>
}