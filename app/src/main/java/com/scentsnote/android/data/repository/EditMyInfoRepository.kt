package com.scentsnote.android.data.repository

import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl
import com.scentsnote.android.data.vo.request.RequestEditMyInfo
import com.scentsnote.android.data.vo.request.RequestEditPassword

class EditMyInfoRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun putMyInfo(token: String, userIdx: Int, body: RequestEditMyInfo) = remoteDataSource.putMyInfo(token, userIdx, body)
    suspend fun putPassword(token: String, body: RequestEditPassword) = remoteDataSource.putPassword(token, body)
}