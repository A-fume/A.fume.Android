package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.vo.request.RequestReview

class NoteRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postReview(token : String, perfumeIdx: Int, body : RequestReview) = remoteDataSource.postReview(token, perfumeIdx, body)
    suspend fun deleteReview(token : String, reviewIdx : Int) = remoteDataSource.deleteReview(token, reviewIdx)
}