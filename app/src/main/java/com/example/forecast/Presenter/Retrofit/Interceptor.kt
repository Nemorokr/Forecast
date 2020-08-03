package com.example.forecast.Presenter.Retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Interceptor {

    val interceptor = HttpLoggingInterceptor()
//   interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

}