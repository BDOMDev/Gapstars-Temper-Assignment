package com.oshan.gapstars_temper_assesment.utils

import android.content.Context
import android.location.Location
import com.oshan.gapstars_temper_assesment.GapstarsApplication
import com.oshan.gapstars_temper_assesment.R
import retrofit2.HttpException
import java.io.IOException
import java.math.BigDecimal
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

object Utils {

    /**
     * Calculates the arial distance between two points
     */
    fun calculateDistanceBetweenTwoLocations(deviceLocation: Location, jobLocation: Location): Float{
        val distance: Float = deviceLocation.distanceTo(jobLocation)
        return distance
    }

    fun getCurrentDateAsString(format: String): String{
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val currentDate: String = sdf.format(Date())
        return currentDate
    }

    fun currencyFormat(amount: BigDecimal): String? {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.toDouble())
    }

    fun getCurrencySymbol(currencyCode: String?): String{
        return try {
            val locale = Locale("en")
            val pound = Currency.getInstance(currencyCode)
            pound.getSymbol(locale)
        }catch (e: Exception){
            return currencyCode ?: ""
        }
    }

}