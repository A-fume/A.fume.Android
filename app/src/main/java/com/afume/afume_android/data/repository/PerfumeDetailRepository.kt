package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.request.RequestReportReview
import com.afume.afume_android.data.vo.response.ResponseBase
import com.afume.afume_android.data.vo.response.ResponsePerfumeDetail
import com.afume.afume_android.data.vo.response.ResponsePerfumeDetailWithReviews
import io.reactivex.Single

class PerfumeDetailRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    fun getPerfumeDetail(token: String, perfumeIdx: Int): Single<ResponsePerfumeDetail> =
        AfumeServiceImpl.service.getPerfumeDetail(token, perfumeIdx).map { it }

    fun getPerfumeDetailWithReviews(token: String, perfumeIdx: Int): Single<ResponsePerfumeDetailWithReviews> =
        AfumeServiceImpl.service.getPerfumeDetailWithReview(token, perfumeIdx).map { it }

    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        AfumeServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }

    suspend fun postReviewLike(token: String, reviewIdx: Int) = remoteDataSource.postReviewLike(token, reviewIdx)
    suspend fun reportReview(token: String, reviewIdx: Int, body: RequestReportReview) = remoteDataSource.reportReview(token, reviewIdx, body)
}