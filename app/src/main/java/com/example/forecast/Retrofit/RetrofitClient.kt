package com.example.forecast.Retrofit

import com.example.forecast.Retrofit.Interceptor.client
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var ourInstansce: Retrofit? = null

    val instance: Retrofit
        get() {
            if (ourInstansce==null) {
                ourInstansce = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return ourInstansce!!
        }
}