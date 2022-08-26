package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl
import com.scents.note.data.remote.network.ScentsNoteServiceImpl
import com.scents.note.data.vo.response.ResponseBase
import io.reactivex.Single

class HomeRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getRecommendPerfumeList(token : String) = remoteDataSource.getRecommendPerfumeList(token)
    suspend fun getCommonPerfumeList(token : String) = remoteDataSource.getCommonPerfumeList(token)
    suspend fun getRecentPerfumeList(token : String) = remoteDataSource.getRecentPerfumeList(token)
    suspend fun getNewPerfumeList() = remoteDataSource.getNewPerfumeList()
    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        ScentsNoteServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }
}