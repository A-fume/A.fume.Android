package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl

class MyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()
    suspend fun getLikedPerfume(token: String, userIdx: Int) = remoteDataSource.getLikedPerfume(token, userIdx)
    suspend fun getMyPerfume(token: String) = remoteDataSource.getMyPerfume(token)
}