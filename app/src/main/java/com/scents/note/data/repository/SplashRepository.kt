package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl

class SplashRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getVersion(apkVersion:String) = remoteDataSource.getVersion(apkVersion)
}