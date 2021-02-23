package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.vo.request.RequestSurvey

class SurveyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getSeries() = remoteDataSource.getSeries()
    suspend fun getSurveyPerfume()=remoteDataSource.getSurveyPerfume(
        //todo add token
    ""
    )
    suspend fun getKeyword()=remoteDataSource.getKeyword()
    suspend fun postSurvey(token: String, body: RequestSurvey)=remoteDataSource.postSurvey(token, body)
}