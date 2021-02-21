package com.afume.afume_android.data.remote

import android.util.Log
import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.NewPerfumeListData
import com.afume.afume_android.data.vo.response.SeriesInfo
import io.reactivex.Observable

class RemoteDataSourceImpl : RemoteDataSource{
    val api = AfumeServiceImpl.service

    override fun getNewPerfumeList(): Observable<NewPerfumeListData> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeries(): MutableList<SeriesInfo>{
        var data = mutableListOf<SeriesInfo>()
        data=api.getSeries().data.rows
//            .enqueue(object :Callback<ResponseBase<ResponseSeries>>{
//                override fun onFailure(call: Call<ResponseBase<ResponseSeries>>, t: Throwable) {
//                    Log.e("e",t.toString())
//                }
//
//                override fun onResponse(
//                    call: Call<ResponseBase<ResponseSeries>>,
//                    response: Response<ResponseBase<ResponseSeries>>
//                ) {
//                    when(response.isSuccessful){
//                        true-> response.body().let {
//                            if(it !=null) data=it.data.rows
//                            Log.e("data",data.toString())
//                        }
//                        false -> Log.e("e",response.toString())
//                    }
//                }
//
//            })
        Log.e("data",data.toString())
        return data
    }


}