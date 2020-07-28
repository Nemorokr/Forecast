package com.example.forecast.Model

import com.google.gson.annotations.SerializedName

class WeatherDescription (
    @SerializedName("description")
    val status: String
)