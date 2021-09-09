package com.oshan.gapstars_temper_assesment.service.entities

import java.math.BigDecimal

data class JobNetworkEntity(
    var id: String?,
    var earnings_per_hour: EarningsPerHourNetworkEntity?,
    var job: JobDataNetworkEntity?,
    var recurring_shift_schedule: RecurringShiftSchedule?
)

data class JobDataNetworkEntity(
    var id: String?,
    var title: String?,
    var links: LinksNetworkEntity?,
    var category: CategoryNetworkEntity?,
    var report_at_address: ReportAtAddressNetworkEntity?
)

data class LinksNetworkEntity(
    var hero_380_image: String?
)


data class CategoryNetworkEntity(
    var id: String?,
    var name: String?
)


data class EarningsPerHourNetworkEntity(
    var currency: String?,
    var amount: BigDecimal?
)

data class GeoNetworkEntity(
    var lat: Double?,
    var lon: Double?
)

data class ReportAtAddressNetworkEntity(
    var geo: GeoNetworkEntity
)

data class RecurringShiftSchedule(
    var starts_at: String?,
    var ends_at: String?
)


