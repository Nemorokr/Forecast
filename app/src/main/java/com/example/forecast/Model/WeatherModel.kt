package com.example.forecast.Model

import com.google.gson.annotations.SerializedName

data class WeatherModel (
    @SerializedName("name")
    val address: String,
    @SerializedName("dt")
    val updatedAt: Long,
    @SerializedName("weather")
    val weatherDescription: List<WeatherDescription>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("sys")
    val sys:Sys,
    @SerializedName ("wind")
    val wind: Wind
)