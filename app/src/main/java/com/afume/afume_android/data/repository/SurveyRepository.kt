package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class SurveyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    fun getSeries() = remoteDataSource.getSeries()
}