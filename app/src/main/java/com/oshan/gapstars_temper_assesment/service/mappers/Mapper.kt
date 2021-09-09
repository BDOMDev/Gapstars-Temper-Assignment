package com.oshan.gapstars_temper_assesment.service.mappers

interface Mapper<F, T> {
    fun map(from: F): T
}