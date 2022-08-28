package com.scentsnote.android.data.repository

import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl
import com.scentsnote.android.data.vo.request.RequestReview

class NoteRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getReview(reviewIdx: Int) = remoteDataSource.getReview(reviewIdx)
    suspend fun postReview(token : String, perfumeIdx: Int, body : RequestReview) = remoteDataSource.postReview(token, perfumeIdx, body)
    suspend fun putReview(token : String, reviewIdx: Int, body : RequestReview) = remoteDataSource.putReview(token, reviewIdx, body)
    suspend fun deleteReview(token : String, reviewIdx : Int) = remoteDataSource.deleteReview(token, reviewIdx)
}