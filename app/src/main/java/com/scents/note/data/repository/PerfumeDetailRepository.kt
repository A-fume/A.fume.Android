package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl
import com.scents.note.data.remote.network.ScentsNoteServiceImpl
import com.scents.note.data.vo.request.RequestReportReview
import com.scents.note.data.vo.response.ResponseBase
import com.scents.note.data.vo.response.ResponsePerfumeDetail
import com.scents.note.data.vo.response.ResponsePerfumeDetailWithReviews
import io.reactivex.Single

class PerfumeDetailRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    fun getPerfumeDetail(token: String, perfumeIdx: Int): Single<ResponsePerfumeDetail> =
        ScentsNoteServiceImpl.service.getPerfumeDetail(token, perfumeIdx).map { it }

    fun getPerfumeDetailWithReviews(token: String, perfumeIdx: Int): Single<ResponsePerfumeDetailWithReviews> =
        ScentsNoteServiceImpl.service.getPerfumeDetailWithReview(token, perfumeIdx).map { it }

    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        ScentsNoteServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }

    suspend fun postReviewLike(token: String, reviewIdx: Int) = remoteDataSource.postReviewLike(token, reviewIdx)
    suspend fun reportReview(token: String, reviewIdx: Int, body: RequestReportReview) = remoteDataSource.reportReview(token, reviewIdx, body)
}