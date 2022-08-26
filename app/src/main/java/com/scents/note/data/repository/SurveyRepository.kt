package com.scents.note.data.repository

import com.scents.note.ScentsNoteApplication
import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl
import com.scents.note.data.vo.request.RequestSurvey

class SurveyRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getSeries() = remoteDataSource.getSeries()
    suspend fun getSurveyPerfume()=remoteDataSource.getSurveyPerfume(
        ScentsNoteApplication.prefManager.accessToken
    )
    suspend fun getKeyword()=remoteDataSource.getKeyword()
    suspend fun postSurvey(token: String, body: RequestSurvey)=remoteDataSource.postSurvey(token, body)
}