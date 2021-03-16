package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.vo.request.RequestLogin
import com.afume.afume_android.data.vo.request.RequestRegister

class SignRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getValidateEmail(email : String) = remoteDataSource.getValidateEmail(email)
    suspend fun getValidateNickname(nickname : String) = remoteDataSource.getValidateNickname(nickname)
    suspend fun postRegisterInfo(body: RequestRegister) = remoteDataSource.postRegisterInfo(body)
    suspend fun postLoginInfo(body : RequestLogin) = remoteDataSource.postLoginInfo(body)
}