package com.example.forecast.Presenter

import com.example.forecast.Retrofit.IWeatherRequest
import com.example.forecast.Retrofit.RetrofitClient
import com.example.forecast.View.IWeatherView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherPresenter(val viewInterface: IWeatherView):
    IWeatherPresenter {

    val CITY = "yekaterinburg, ru"
    val UNITS = "metric"
    val KEY = "4d8f0b60c4e68439a8707fb6e0878962"

    val retrofit = RetrofitClient.instance
    val getWeatherApi = retrofit.create(IWeatherRequest::class.java)

    override fun fetchData() {

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(getWeatherApi.getCurrentWeather(CITY, UNITS, KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewInterface.onLoading() }
            .doFinally { viewInterface.onLoaded() }
            .subscribe(
                { weather -> viewInterface.displayWeather(weather)},
                { viewInterface.onError(t = it)}
            )
        )
    }
}
