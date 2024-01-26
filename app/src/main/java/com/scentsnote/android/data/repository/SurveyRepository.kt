package com.scentsnote.android.data.repository

import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl
import com.scentsnote.android.data.vo.request.RequestSurvey

class SurveyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getSeries() = remoteDataSource.getSeries()
    suspend fun getSurveyPerfume()=remoteDataSource.getSurveyPerfume(
        ScentsNoteApplication.prefManager.accessToken
    )
    suspend fun getKeyword()=remoteDataSource.getKeyword()
    suspend fun postSurvey(token: String, body: RequestSurvey)=remoteDataSource.postSurvey(token, body)
}