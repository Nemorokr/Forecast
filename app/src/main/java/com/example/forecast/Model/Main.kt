package com.example.forecast.Model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: String,
    @SerializedName("feels_like")
    val tempFielsLike: String,
    @SerializedName ("humidity")
    val humidity: String
)