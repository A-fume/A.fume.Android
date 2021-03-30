package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.vo.request.RequestEditMyInfo

class EditMyInfoRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun putMyInfo(token: String, userIdx: Int, body: RequestEditMyInfo) = remoteDataSource.putMyInfo(token, userIdx, body)
}