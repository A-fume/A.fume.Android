package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class HomeRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getNewPerfumeList() = remoteDataSource.getNewPerfumeList()
}