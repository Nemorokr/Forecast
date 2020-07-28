package com.example.forecast.Retrofit

import android.provider.Contacts.SettingsColumns.KEY
import com.example.forecast.Model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IWeatherRequest {

    @GET("weather")
    fun getCurrentWeather(@Query("q") CITY: String, @Query ("units") UNITS: String, @Query("appid") KEY: String): Call<WeatherModel>
}