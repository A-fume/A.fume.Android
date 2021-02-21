package com.afume.afume_android.data.remote

import com.afume.afume_android.data.vo.NewPerfumeListData
import com.afume.afume_android.data.vo.response.SeriesInfo
import io.reactivex.Observable

interface RemoteDataSource {
    fun getNewPerfumeList() : Observable<NewPerfumeListData>
    suspend fun getSeries() : MutableList<SeriesInfo>

}