package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class MyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()
    suspend fun getLikedPerfume(token: String, userIdx: Int) = remoteDataSource.getLikedPerfume(token, userIdx)
    suspend fun getMyPerfume(token: String) = remoteDataSource.getMyPerfume(token)
}