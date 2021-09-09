package com.oshan.gapstars_temper_assesment.di

import com.oshan.gapstars_temper_assesment.service.ApiClient
import com.oshan.gapstars_temper_assesment.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitClient(): ApiService {
        return ApiClient().retrofit.create(ApiService::class.java)
    }

}