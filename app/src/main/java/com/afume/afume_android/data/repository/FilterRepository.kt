package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class FilterRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getSeries() = remoteDataSource.getFilterSeries()
    suspend fun getBrand() = remoteDataSource.getFilterBrand()
    suspend fun getKeyword() = remoteDataSource.getKeyword()
}