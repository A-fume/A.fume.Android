package com.scentsnote.android.data.remote

import android.util.Log
import com.scentsnote.android.data.remote.network.ScentsNoteServiceImpl
import com.scentsnote.android.data.vo.request.*
import com.scentsnote.android.data.vo.response.*

class RemoteDataSourceImpl : RemoteDataSource{
    val api = ScentsNoteServiceImpl.service

    override suspend fun getValidateEmail(email: String): Boolean{
        return api.getValidateEmail(email).data
    }

    override suspend fun getValidateNickname(nickname: String): Boolean{
        return api.getValidateNickname(nickname).data
    }

    override suspend fun postRegisterInfo(body: RequestRegister): ResponseRegister {
        return api.postRegisterInfo(body).data
    }

    override suspend fun postLoginInfo(body: RequestLogin): ResponseLogin {
        return api.postLoginInfo(body).data
    }

    override suspend fun getSeries(): MutableList<SeriesInfo>{
        var data = mutableListOf<SeriesInfo>()
        data=api.getSeries().data.rows
        Log.e("data",data.toString())
        return data
    }

    override suspend fun getSurveyPerfume(token: String): MutableList<PerfumeInfo> {
        Log.e("d",api.getSurveyPerfume(token).message)
        Log.e("d",api.getSurveyPerfume(token).data.rows.toString())
        return api.getSurveyPerfume(token).data.rows
    }

    override suspend fun getKeyword(): MutableList<KeywordInfo>{
        Log.e("keyword",api.getKeyword().data.toString())
        return api.getKeyword().data.rows
    }

    override suspend fun postSurvey(token: String, body: RequestSurvey): String{
        return  api.postSurvey(token, body).message
    }

    override suspend fun getLikedPerfume(token: String, userIdx: Int): MutableList<WishPerfume> {
        return api.getLikedPerfume(token, userIdx).data.rows
    }

    override suspend fun getMyPerfume(token: String): MutableList<ResponseMyPerfume> {
        return api.getMyPerfume(token).data
    }

    override suspend fun putMyInfo(token: String, userIdx: Int, body: RequestEditMyInfo): ResponseEditMyInfo {
        return api.putMyInfo(token,userIdx,body).data
    }

    override suspend fun putPassword(token: String, body: RequestEditPassword): String {
        return api.putPassword(token,body).message
    }

    override suspend fun getFilterSeries(): ResponseSeries {
        return api.getFilterSeries().data
    }

    override suspend fun getFilterBrand(): MutableList<InitialBrand> {
        return  api.getFilterBrand().data
    }

    override suspend fun postSearchPerfume(token: String?,body:RequestSearch): MutableList<PerfumeInfo> {
        return  api.postSearchPerfume(token,body).data.rows
    }


    override suspend fun getRecommendPerfumeList(token: String): MutableList<RecommendPerfumeItem> {
        return api.getRecommendPerfumeList(token).data.rows
    }

    override suspend fun getCommonPerfumeList(token: String): MutableList<HomePerfumeItem> {
        return api.getCommonPerfumeList(token).data.rows
    }

    override suspend fun getRecentPerfumeList(token: String): MutableList<HomePerfumeItem> {
        return api.getRecentList(token).data.rows
    }

    override suspend fun getNewPerfumeList(): MutableList<HomePerfumeItem> {
        return api.getNewPerfumeList().data.rows
    }

    override suspend fun getReview(reviewIdx: Int): ResponseReview {
        return api.getReview(reviewIdx).data
    }

    override suspend fun postReview(token: String, perfumeIdx: Int, body: RequestReview): ResponseReviewAdd {
        return api.postReview(token, perfumeIdx, body).data
    }

    override suspend fun putReview(token: String, reviewIdx: Int, body: RequestReview): String {
        return api.putReview(token, reviewIdx, body).message
    }

    override suspend fun deleteReview(token: String, reviewIdx: Int): String {
        return api.deleteReview(token, reviewIdx).message
    }

    override suspend fun getSimilarPerfumeList(perfumeIdx: Int): MutableList<RecommendPerfumeItem> {
        return api.getSimilarPerfumeList(perfumeIdx).data.rows
    }

    override suspend fun postReviewLike(token: String, reviewIdx: Int): Boolean {
        return api.postReviewLike(token, reviewIdx).data
    }

    override suspend fun reportReview(token: String, reviewIdx: Int, body: RequestReportReview): String {
        return api.reportReview(token, reviewIdx, body).message
    }

    override suspend fun getVersion(apkVersion: String): Boolean {
        return api.getVersion(apkVersion).data
    }
}