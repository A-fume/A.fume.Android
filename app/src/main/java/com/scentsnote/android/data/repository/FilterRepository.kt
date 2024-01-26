package com.scentsnote.android.data.repository

import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl

class FilterRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getSeries() = remoteDataSource.getFilterSeries()
    suspend fun getBrand() = remoteDataSource.getFilterBrand()
    suspend fun getKeyword() = remoteDataSource.getKeyword()
}