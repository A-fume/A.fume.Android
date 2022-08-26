package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl
import com.scents.note.data.vo.request.RequestReview

class NoteRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getReview(reviewIdx: Int) = remoteDataSource.getReview(reviewIdx)
    suspend fun postReview(token : String, perfumeIdx: Int, body : RequestReview) = remoteDataSource.postReview(token, perfumeIdx, body)
    suspend fun putReview(token : String, reviewIdx: Int, body : RequestReview) = remoteDataSource.putReview(token, reviewIdx, body)
    suspend fun deleteReview(token : String, reviewIdx : Int) = remoteDataSource.deleteReview(token, reviewIdx)
}