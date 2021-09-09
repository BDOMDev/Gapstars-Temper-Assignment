package com.oshan.gapstars_temper_assesment.di

import com.oshan.gapstars_temper_assesment.service.ApiService
import com.oshan.gapstars_temper_assesment.service.repository.JobsRepo
import com.oshan.gapstars_temper_assesment.service.repository.JobsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun getDummyRepo(
        retrofitService: ApiService
    ): JobsRepo {
        return JobsRepoImpl(retrofitService)
    }


}