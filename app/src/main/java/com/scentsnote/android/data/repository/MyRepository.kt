package com.scentsnote.android.data.repository

import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl

class MyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()
    suspend fun getLikedPerfume(token: String, userIdx: Int) = remoteDataSource.getLikedPerfume(token, userIdx)
    suspend fun getMyPerfume(token: String) = remoteDataSource.getMyPerfume(token)
}