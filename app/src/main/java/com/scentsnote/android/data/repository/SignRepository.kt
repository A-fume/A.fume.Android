package com.scentsnote.android.data.repository

import com.scentsnote.android.data.remote.RemoteDataSource
import com.scentsnote.android.data.remote.RemoteDataSourceImpl
import com.scentsnote.android.data.vo.request.RequestLogin
import com.scentsnote.android.data.vo.request.RequestNewToken
import com.scentsnote.android.data.vo.request.RequestRegister

class SignRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getValidateEmail(email : String) = remoteDataSource.getValidateEmail(email)
    suspend fun getValidateNickname(nickname : String) = remoteDataSource.getValidateNickname(nickname)
    suspend fun postRegisterInfo(body: RequestRegister) = remoteDataSource.postRegisterInfo(body)
    suspend fun postLoginInfo(body : RequestLogin) = remoteDataSource.postLoginInfo(body)
    suspend fun getNewToken(body: RequestNewToken) = remoteDataSource.getNewToken(body)
}