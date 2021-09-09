package com.oshan.gapstars_temper_assesment.service.mappers

import com.oshan.gapstars_temper_assesment.models.Job
import com.oshan.gapstars_temper_assesment.service.entities.JobNetworkEntity
import com.oshan.gapstars_temper_assesment.utils.Utils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object JobEntityMapper: Mapper<JobNetworkEntity, Job> {
    override fun map(from: JobNetworkEntity): Job {
        return Job(
            id = from.id,
            currencySymbol = Utils.getCurrencySymbol(from.earnings_per_hour?.currency),
            price = from.earnings_per_hour?.amount,
            jobTitle = from.job?.title,
            imageUrl = from.job?.links?.hero_380_image,
            categoryName = from.job?.category?.name,
            lat = from.job?.report_at_address?.geo?.lat,
            long = from.job?.report_at_address?.geo?.lon,
            shiftStartFrom = removeSecondsFromTime(from.recurring_shift_schedule?.starts_at),
            shiftStopAt = removeSecondsFromTime(from.recurring_shift_schedule?.ends_at)
        )
    }

    private fun removeSecondsFromTime(time: String?): String {
        val dateFormat = SimpleDateFormat("H:mm:ss", Locale.ENGLISH)
        if (time == null || time == ""){
            return ""
        }
        val myDate = dateFormat.parse(time)
        val df: DateFormat = SimpleDateFormat("H:mm", Locale.ENGLISH)
        val dateString = df.format(myDate)
        return dateString
    }
 }