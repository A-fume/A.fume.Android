package com.afume.afume_android.data.remote

import com.afume.afume_android.data.vo.NewPerfumeListData
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.data.vo.response.SeriesInfo
import io.reactivex.Observable

interface RemoteDataSource {
    fun getNewPerfumeList(): Observable<NewPerfumeListData>
    suspend fun getSeries(): MutableList<SeriesInfo>
    suspend fun getSurveyPerfume(token: String): MutableList<PerfumeInfo>
    suspend fun getKeyword(): MutableList<ResponseKeyword>
}