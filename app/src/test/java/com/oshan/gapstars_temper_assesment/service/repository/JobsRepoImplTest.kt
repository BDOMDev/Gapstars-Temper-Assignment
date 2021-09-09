package com.oshan.gapstars_temper_assesment.service.repository

import com.oshan.gapstars_temper_assesment.service.ApiClient
import com.oshan.gapstars_temper_assesment.service.ApiService
import com.oshan.gapstars_temper_assesment.service.entities.JobNetworkEntity
import io.reactivex.Single
import junit.framework.TestCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class JobsRepoImplTest : TestCase() {

    private lateinit var apiClient: ApiService

    @Before
    override fun setUp() {
        apiClient = ApiClient().retrofit.create(ApiService::class.java)

    }

    @Test
    fun testGetJobs() {
        runBlocking {

            `when`(apiClient.loadJobData()).thenReturn(Single.just(ArrayList<JobNetworkEntity>()))

            val result = apiClient.loadJobData()

            Assert.assertEquals(result, ArrayList<JobNetworkEntity>())
        }
    }


}