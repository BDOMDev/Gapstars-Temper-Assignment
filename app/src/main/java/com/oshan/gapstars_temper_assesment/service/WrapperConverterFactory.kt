package com.oshan.gapstars_temper_assesment.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * used to map json response format into the usable models
 * ex :- extract any type of data from the data field of the response and map them into data classes using Gson
 */
class WrapperConverterFactory(gson: Gson) : Converter.Factory() {

    private val gsonConverterFactory: GsonConverterFactory = buildGsonConverterFactory(gson)

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = ResponseDataWrapper::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? = gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)
        return ResponseBodyConverter(gsonConverter as Converter<ResponseBody, ResponseDataWrapper<Any>>)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(type!!, parameterAnnotations, methodAnnotations, retrofit)
    }

    private fun buildGsonConverterFactory(gson: Gson): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        return GsonConverterFactory.create(gsonBuilder.create())
    }
}



class ResponseBodyConverter<T>(private val converter: Converter<ResponseBody, ResponseDataWrapper<T>>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): T? {
        val response = converter.convert(responseBody)
        return response?.data
    }
}