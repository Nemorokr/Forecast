package com.example.forecast.Retrofit

import com.example.forecast.Model.WeatherModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherRequest {

    @GET("weather")
    fun getCurrentWeather(@Query("q") CITY: String, @Query ("units") UNITS: String, @Query("appid") KEY: String): Observable<WeatherModel>
}