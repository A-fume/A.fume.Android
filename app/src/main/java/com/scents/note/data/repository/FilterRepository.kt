package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl

class FilterRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getSeries() = remoteDataSource.getFilterSeries()
    suspend fun getBrand() = remoteDataSource.getFilterBrand()
    suspend fun getKeyword() = remoteDataSource.getKeyword()
}