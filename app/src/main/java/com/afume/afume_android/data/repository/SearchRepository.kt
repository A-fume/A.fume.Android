package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.vo.request.RequestSearch

class SearchRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postResultPerfume(body:RequestSearch)=remoteDataSource.postSearchPerfume(body)

}