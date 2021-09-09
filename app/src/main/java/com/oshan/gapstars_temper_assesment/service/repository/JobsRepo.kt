package com.oshan.gapstars_temper_assesment.service.repository

import com.oshan.gapstars_temper_assesment.models.Job
import com.oshan.gapstars_temper_assesment.utils.ResultWrapper
import io.reactivex.Observable
import io.reactivex.Single

interface JobsRepo {

    fun getJobsData(currentDate: String): Single<ArrayList<Job>>

}