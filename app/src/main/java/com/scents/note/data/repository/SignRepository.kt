package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl
import com.scents.note.data.vo.request.RequestLogin
import com.scents.note.data.vo.request.RequestRegister

class SignRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getValidateEmail(email : String) = remoteDataSource.getValidateEmail(email)
    suspend fun getValidateNickname(nickname : String) = remoteDataSource.getValidateNickname(nickname)
    suspend fun postRegisterInfo(body: RequestRegister) = remoteDataSource.postRegisterInfo(body)
    suspend fun postLoginInfo(body : RequestLogin) = remoteDataSource.postLoginInfo(body)
}