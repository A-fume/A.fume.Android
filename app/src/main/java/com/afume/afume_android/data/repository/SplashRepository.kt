package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class SplashRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getVersion(apkVersion:String) = remoteDataSource.getVersion(apkVersion)
}