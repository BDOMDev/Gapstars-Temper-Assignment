package com.oshan.gapstars_temper_assesment.service.repository


import com.oshan.gapstars_temper_assesment.models.Job
import com.oshan.gapstars_temper_assesment.service.ApiService
import com.oshan.gapstars_temper_assesment.service.mappers.JobEntityMapper
import com.oshan.gapstars_temper_assesment.utils.ResultWrapper
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JobsRepoImpl @Inject constructor(
    private var api: ApiService
): JobsRepo {

    /**
     * Gets the results from the api and transform it from a list of JobDto to a list of Job Entities using RxKotlin
     */
    override fun getJobsData(currentDate: String): Single<ArrayList<Job>> {
        return api
            .loadJobData(currentDate)
            .map { items ->
                val jobList = ArrayList<Job>()
                items.forEach {
                    jobList.add(JobEntityMapper.map(it))
                }
                jobList
            }
    }
}