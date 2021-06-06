package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.response.ResponseBase
import com.afume.afume_android.data.vo.response.ResponsePerfumeDetail
import com.afume.afume_android.data.vo.response.ResponsePerfumeDetailWithReviews
import io.reactivex.Single

class PerfumeDetailRepository {
    fun getPerfumeDetail(perfumeIdx: Int): Single<ResponsePerfumeDetail> =
        AfumeServiceImpl.service.getPerfumeDetail(perfumeIdx).map { it }

    fun getPerfumeDetailWithReviews(perfumeIdx: Int): Single<ResponsePerfumeDetailWithReviews> =
        AfumeServiceImpl.service.getPerfumeDetailWithReview(perfumeIdx).map { it }

    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        AfumeServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }
}