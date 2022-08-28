package com.scentsnote.android.data.repository

import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl
import com.scentsnote.android.data.remote.network.ScentsNoteServiceImpl
import com.scentsnote.android.data.vo.request.RequestSearch
import com.scentsnote.android.data.vo.response.ResponseBase
import io.reactivex.Single

class SearchRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postResultPerfume(token:String?,body:RequestSearch)=remoteDataSource.postSearchPerfume(token,body)
    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        ScentsNoteServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }

}