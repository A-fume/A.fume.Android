package com.scents.note.data.repository

import com.scents.note.data.remote.RemoteDataSource
import com.scents.note.data.remote.RemoteDataSourceImpl
import com.scents.note.data.remote.network.ScentsNoteServiceImpl
import com.scents.note.data.vo.request.RequestSearch
import com.scents.note.data.vo.response.ResponseBase
import io.reactivex.Single

class SearchRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postResultPerfume(token:String?,body:RequestSearch)=remoteDataSource.postSearchPerfume(token,body)
    fun postPerfumeLike(token: String, perfumeIdx: Int): Single<ResponseBase<Boolean>> =
        ScentsNoteServiceImpl.service.postPerfumeLike(token, perfumeIdx).map { it }

}