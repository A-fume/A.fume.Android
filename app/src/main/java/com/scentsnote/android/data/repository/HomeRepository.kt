package com.scentsnote.android.data.repository

import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl
import com.scentsnote.android.data.remote.network.ScentsNoteServiceImpl
import com.scentsnote.android.data.vo.response.ResponseBase
import io.reactivex.Single

class HomeRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getRecommendPerfumeList(token : String) = remoteDataSource.getRecommendPerfumeList(token)
    suspend fun getCommonPerfumeList(token : String) = remoteDataSource.getCommonPerfumeList(token)
    suspend fun getRecentPerfumeList(token : String) = remoteDataSource.getRecentPerfumeList(token)
    suspend fun getNewPerfumeList(requestSize: Int?) = remoteDataSource.getNewPerfumeList(requestSize)
    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        ScentsNoteServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }
}