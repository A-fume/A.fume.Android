package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class SurveyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getSeries() = remoteDataSource.getSeries()
    suspend fun getSurveyPerfume()=remoteDataSource.getSurveyPerfume(
        //todo add token
    ""
    )
    suspend fun getKeyword()=remoteDataSource.getKeyword()
}