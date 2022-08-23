package com.afume.afume_android.data.remote

import com.afume.afume_android.data.vo.request.*
import com.afume.afume_android.data.vo.response.*

interface RemoteDataSource {
    suspend fun getValidateEmail(email : String): Boolean
    suspend fun getValidateNickname(nickname : String): Boolean
    suspend fun postRegisterInfo(body: RequestRegister): ResponseRegister
    suspend fun postLoginInfo(body: RequestLogin): ResponseLogin
    suspend fun getSeries(): MutableList<SeriesInfo>
    suspend fun getSurveyPerfume(token: String): MutableList<PerfumeInfo>
    suspend fun getKeyword(): MutableList<KeywordInfo>
    suspend fun postSurvey(token: String, body: RequestSurvey): String
    suspend fun getLikedPerfume(token : String, userIdx : Int): MutableList<PerfumeInfo>
    suspend fun getMyPerfume(token : String): MutableList<ResponseMyPerfume>
    suspend fun putMyInfo(token: String, userIdx: Int, body: RequestEditMyInfo) : ResponseEditMyInfo
    suspend fun putPassword(token: String, body: RequestEditPassword) : String
    suspend fun getReview(reviewIdx: Int) : ResponseReview
    suspend fun postReview(token: String, perfumeIdx: Int, body: RequestReview) : ResponseReviewAdd
    suspend fun putReview(token: String, reviewIdx: Int, body: RequestReview) : String
    suspend fun deleteReview(token: String, reviewIdx: Int) : String
    suspend fun postReviewLike(token: String, reviewIdx: Int) : Boolean
    suspend fun reportReview(token: String,reviewIdx: Int, body: RequestReportReview) : String
    suspend fun getVersion(apkVersion: String): Boolean

    // filter
    suspend fun getFilterSeries():ResponseSeries
    suspend fun getFilterBrand():MutableList<InitialBrand>
    // search
    suspend fun postSearchPerfume(token: String?,body: RequestSearch):MutableList<PerfumeInfo>
    suspend fun getRecommendPerfumeList(token: String) : MutableList<RecommendPerfumeItem>
    suspend fun getCommonPerfumeList(token: String) : MutableList<HomePerfumeItem>
    suspend fun getRecentPerfumeList(token: String) : MutableList<HomePerfumeItem>
    suspend fun getNewPerfumeList() : MutableList<HomePerfumeItem>
}