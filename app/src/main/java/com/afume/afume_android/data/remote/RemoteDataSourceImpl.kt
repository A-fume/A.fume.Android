package com.afume.afume_android.data.remote

import android.util.Log
import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.request.RequestSurvey
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.data.vo.response.ResponseKeyword
import com.afume.afume_android.data.vo.response.SeriesInfo

class RemoteDataSourceImpl : RemoteDataSource{
    val api = AfumeServiceImpl.service

    override suspend fun getValidateEmail(email: String): Boolean{
        return api.getValidateEmail(email).data
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

    override suspend fun getKeyword(): MutableList<ResponseKeyword> {
        Log.e("keyword",api.getKeyword().data.toString())
        return api.getKeyword().data
    }

    override suspend fun postSurvey(token: String, body: RequestSurvey): String{
        return  api.postSurvey(token, body).message
    }

    override suspend fun getLikedPerfume(token: String, userIdx: Int): MutableList<PerfumeInfo> {
        Log.e("wishlist",api.getLikedPerfume(token, userIdx).message)
        return api.getLikedPerfume(token, userIdx).data.rows
    }


}