package com.oshan.gapstars_temper_assesment.models

import java.math.BigDecimal

data class Job(
    var id: String?,
    var currencySymbol: String?,
    var price: BigDecimal?,
    var jobTitle: String?,
    var imageUrl: String?,
    var categoryName: String?,
    var lat: Double?,
    var long: Double?,
    var shiftStartFrom: String,
    var shiftStopAt: String
)
