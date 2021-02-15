package com.afume.afume_android.data.remote

import com.afume.afume_android.data.vo.NewPerfumeListData
import io.reactivex.Observable

interface RemoteDataSource {
    fun getNewPerfumeList() : Observable<NewPerfumeListData>
    fun getSeries()

}