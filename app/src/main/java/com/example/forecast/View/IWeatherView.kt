package com.example.forecast.View

import com.example.forecast.Model.WeatherModel

interface IWeatherView {

    fun displayWeather(weatherModel: WeatherModel)

    fun onLoading()

    fun onLoaded()

    fun onError(t: Throwable)
}