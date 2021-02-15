package com.afume.afume_android.data.remote

import android.util.Log
import com.afume.afume_android.data.remote.network.AfumeServiceImpl
import com.afume.afume_android.data.vo.NewPerfumeListData
import com.afume.afume_android.data.vo.response.ResponseBase
import com.afume.afume_android.data.vo.response.ResponseSeries
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource{
    val api = AfumeServiceImpl.service

    override fun getNewPerfumeList(): Observable<NewPerfumeListData> {
        TODO("Not yet implemented")
    }

    override fun getSeries(){
        api.getSeries()
            .enqueue(object :Callback<ResponseBase<ResponseSeries>>{
                override fun onFailure(call: Call<ResponseBase<ResponseSeries>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ResponseBase<ResponseSeries>>,
                    response: Response<ResponseBase<ResponseSeries>>
                ) {
                    when(response.isSuccessful){
                        true-> response.body().let { Log.e("e",it.toString()) }
                    }
                }

            })
    }


}