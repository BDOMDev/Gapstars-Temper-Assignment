package com.oshan.gapstars_temper_assesment.service


/**
 * common response structure of the api json response
 */
data class ResponseDataWrapper<T>(
    var data: T? = null,
)
