package com.example.forecast.Presenter.Retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class Interceptor {

    val interceptor = HttpLoggingInterceptor()

    fun getInterceptor(): OkHttpClient {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return client
    }
}