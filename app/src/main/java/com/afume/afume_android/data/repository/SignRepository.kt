package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl

class SignRepository {
    val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getValidateEmail(email : String) = remoteDataSource.getValidateEmail(email)
    suspend fun getValidateNickname(nickname : String) = remoteDataSource.getValidateNickname(nickname)
}