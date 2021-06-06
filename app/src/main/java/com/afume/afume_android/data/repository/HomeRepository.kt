package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class HomeRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getRecommendPerfumeList(token : String) = remoteDataSource.getRecommendPerfumeList(token)
    suspend fun getCommonPerfumeList(token : String) = remoteDataSource.getCommonPerfumeList(token)
    suspend fun getRecentPerfumeList(token : String) = remoteDataSource.getRecentPerfumeList(token)
    suspend fun getNewPerfumeList() = remoteDataSource.getNewPerfumeList()
}