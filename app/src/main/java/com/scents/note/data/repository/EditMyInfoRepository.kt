package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl
import com.scents.note.data.vo.request.RequestEditMyInfo
import com.scents.note.data.vo.request.RequestEditPassword

class EditMyInfoRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun putMyInfo(token: String, userIdx: Int, body: RequestEditMyInfo) = remoteDataSource.putMyInfo(token, userIdx, body)
    suspend fun putPassword(token: String, body: RequestEditPassword) = remoteDataSource.putPassword(token, body)
}