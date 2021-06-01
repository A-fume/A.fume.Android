package com.afume.afume_android.data.remote

import android.util.Log
import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.request.*
import com.afume.afume_android.data.vo.response.*

class RemoteDataSourceImpl : RemoteDataSource{
    val api = AfumeServiceImpl.service

    override suspend fun getValidateEmail(email: String): Boolean{
        return api.getValidateEmail(email).data
    }

    override suspend fun getValidateNickname(nickname: String): Boolean{
        return api.getValidateNickname(nickname).data
    }

    override suspend fun postRegisterInfo(body: RequestRegister): String {
        return api.postRegisterInfo(body).message
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

    override suspend fun getLikedPerfume(token: String, userIdx: Int): MutableList<PerfumeInfo> {
        Log.e("wishlist",api.getLikedPerfume(token, userIdx).message)
        return api.getLikedPerfume(token, userIdx).data.rows
    }

    override suspend fun getMyPerfume(token: String, userIdx: Int): MutableList<ResponseMyPerfume> {
        return api.getMyPerfume(token,userIdx).data
    }

    override suspend fun putMyInfo(token: String, userIdx: Int, body: RequestEditMyInfo): ResponseEditMyInfo {
        return api.putMyInfo(token,userIdx,body).data
    }

    override suspend fun putPassword(token: String, body: RequestEditPassword): String {
        return api.putPassword(token,body).message
    }

    override suspend fun getRecommendPerfumeList(token: String): MutableList<RecommendPerfumeItem> {
        return api.getRecommendPerfumeList(token).data.rows
    }

    override suspend fun getCommonPerfumeList(token: String): MutableList<RecommendPerfumeItem> {
        return api.getCommonPerfumeList(token).data.rows
    }

    override suspend fun getRecentPerfumeList(token: String): MutableList<RecentPerfumeItem> {
        return api.getRecentList(token).data.rows
    }

    override suspend fun getNewPerfumeList(): MutableList<NewPerfumeItem> {
        return api.getNewPerfumeList().data.rows
    }
}