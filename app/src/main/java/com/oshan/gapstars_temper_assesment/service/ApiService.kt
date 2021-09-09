package com.oshan.gapstars_temper_assesment.service

import com.oshan.gapstars_temper_assesment.service.entities.JobNetworkEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import okhttp3.RequestBody

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("shifts")
    fun loadJobData(@Query("filter[date]") date: String): Single<List<JobNetworkEntity>>

}