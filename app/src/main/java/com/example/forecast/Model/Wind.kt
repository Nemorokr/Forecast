package com.example.forecast.Model

import com.google.gson.annotations.SerializedName

data class Wind (
    @SerializedName("speed")
    val windspeed: String
) {
}