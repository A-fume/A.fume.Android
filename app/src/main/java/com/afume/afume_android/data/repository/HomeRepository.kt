package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.response.ResponseBase
import io.reactivex.Single

class HomeRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getRecommendPerfumeList(token : String) = remoteDataSource.getRecommendPerfumeList(token)
    suspend fun getCommonPerfumeList(token : String) = remoteDataSource.getCommonPerfumeList(token)
    suspend fun getRecentPerfumeList(token : String) = remoteDataSource.getRecentPerfumeList(token)
    suspend fun getNewPerfumeList() = remoteDataSource.getNewPerfumeList()
    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        AfumeServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }
}