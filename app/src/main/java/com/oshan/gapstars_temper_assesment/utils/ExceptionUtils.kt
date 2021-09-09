package com.oshan.gapstars_temper_assesment.utils

import android.content.Context
import com.oshan.gapstars_temper_assesment.R
import retrofit2.HttpException
import java.io.IOException
import javax.net.ssl.HttpsURLConnection

object ExceptionUtils {

    /**
     * gets a appropriate text message for the type of exception
     */
    fun getErrorMessage(context: Context, error: Throwable): String{
        return when (error) {
            is HttpException -> when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> context.getString(R.string.generic_error_message)
                HttpsURLConnection.HTTP_FORBIDDEN -> context.getString(R.string.generic_error_message)
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> context.getString(R.string.bad_request_error)
                HttpsURLConnection.HTTP_BAD_REQUEST -> context.getString(R.string.internal_error_message)
                else -> context.getString(R.string.generic_error_message)
            }
            is IOException -> context.getString(R.string.no_network_connection)
            else -> context.getString(R.string.generic_error_message)
        }
    }

}