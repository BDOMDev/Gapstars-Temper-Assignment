package com.oshan.gapstars_temper_assesment.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oshan.gapstars_temper_assesment.AppConstants
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class ApiClient {

    var retrofit: Retrofit

    class LogJsonInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)
            val rawJson: String? = response.body()?.string()
            Log.d(
                "ApiResponse",
                String.format("raw JSON response is: %s", rawJson)
            )

            // Re-create the response before returning it because body can be read only once
            return response.newBuilder()
                .body(ResponseBody.create(response.body()?.contentType(), rawJson)).build()
        }
    }

    private fun buildWrapperConverterFactory(): WrapperConverterFactory {
        return WrapperConverterFactory(Gson())
    }

    init {
        val client = OkHttpClient.Builder()

        client.connectTimeout(30, TimeUnit.SECONDS)
        client.writeTimeout(30, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)
        client.interceptors().add(LogJsonInterceptor())

        client.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BaseUrl)
            .addConverterFactory(buildWrapperConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
    }


}