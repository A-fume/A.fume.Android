package com.afume.afume_android.data.repository

import com.afume.afume_android.data.remote.RemoteDataSource
import com.afume.afume_android.data.remote.RemoteDataSourceImpl
import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.request.RequestSearch
import com.afume.afume_android.data.vo.response.ResponseBase
import io.reactivex.Single

class SearchRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postResultPerfume(token:String?,body:RequestSearch)=remoteDataSource.postSearchPerfume(token,body)
    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        AfumeServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }

}