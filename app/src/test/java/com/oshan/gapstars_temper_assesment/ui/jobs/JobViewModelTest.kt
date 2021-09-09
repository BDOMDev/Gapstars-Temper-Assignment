package com.oshan.gapstars_temper_assesment.ui.jobs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.oshan.gapstars_temper_assesment.models.Job
import com.oshan.gapstars_temper_assesment.service.repository.JobsRepo
import com.oshan.gapstars_temper_assesment.testUtils.getOrAwaitValue
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import org.junit.rules.TestRule

import org.junit.Rule
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class JobViewModelTest {

    @JvmField
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: JobViewModel

    private lateinit var jobsRepo: JobsRepo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        jobsRepo = Mockito.mock(JobsRepo::class.java)

        viewModel = JobViewModel(jobsRepo)

      //  viewModel.setLoading(false)
    }

    @Test
    fun `get jobs data, empty data list makes livedata empty`() {
        Mockito.`when`(jobsRepo.getJobsData()).thenReturn(Single.just(ArrayList<Job>()))

        viewModel.getJobsData()

        assertThat(viewModel.jobDataStateObservable.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun `get jobs data, not empty data list, live data should be not empty`() {

        Mockito.`when`(jobsRepo.getJobsData()).thenReturn(Single.just(getSampleJobs()))

        viewModel.getJobsData()

        assertThat(viewModel.jobDataStateObservable.getOrAwaitValue()).isNotEmpty()
    }

    @Test
    fun `get jobs data should make loading state live data false`() {
        Mockito.`when`(jobsRepo.getJobsData()).thenReturn(Single.just(getSampleJobs()))

        viewModel.getJobsData()

        viewModel.jobDataStateObservable.getOrAwaitValue()

        assertThat(viewModel.loadingIndicatorObservable.getOrAwaitValue()).isFalse()
    }

    @Test
    fun `get jobs data error state live data must be not empty when thrown exception`() {

        Mockito.`when`(jobsRepo.getJobsData()).thenReturn(Single.error(Throwable()))

        viewModel.getJobsData()

        val exception = viewModel.errorStateObservable.getOrAwaitValue()

        assertThat(exception).isNotNull()
    }


    private fun getSampleJobs(): ArrayList<Job>{
        val sampleData = ArrayList<Job>()
        sampleData.add(Job(
            id = "testId",
            currencySymbol = "$",
            price = BigDecimal(123),
            jobTitle = "Engineer",
            imageUrl = "https://picsum.photos/200/300",
            categoryName = "Engineering",
            lat = 0.0000, 0.0000,
            shiftStartFrom = "11:00 PM",
            shiftStopAt = "12:00 PM")
        )

        return sampleData
    }


}