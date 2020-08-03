package com.example.forecast.View

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.forecast.Model.WeatherModel
import com.example.forecast.R
import com.example.forecast.Presenter.Retrofit.IWeatherRequest
import com.example.forecast.Presenter.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), WeatherInterface {

    lateinit var address: TextView
    lateinit var updatedAt: TextView
    lateinit var status: TextView
    lateinit var temp: TextView
    lateinit var tempFielsLike: TextView
    lateinit var sunrise: TextView
    lateinit var sunset: TextView
    lateinit var wind: TextView
    lateinit var pressure: TextView
    lateinit var humidity: TextView

    lateinit var getWeatherApi: IWeatherRequest

    val CITY = "yekaterinburg, ru"
    val UNITS = "metric"
    val KEY = "4d8f0b60c4e68439a8707fb6e0878962"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        address = findViewById(R.id.address)
        updatedAt = findViewById(R.id.updated_at)
        status = findViewById(R.id.status)
        temp = findViewById(R.id.temp)
        tempFielsLike = findViewById(R.id.temp_fiels_like)
        sunrise = findViewById(R.id.sunrise)
        sunset = findViewById(R.id.sunset)
        wind = findViewById(R.id.wind)
        pressure = findViewById(R.id.pressure)
        humidity = findViewById(R.id.humidity)


        val retrofit = RetrofitClient.instance
        getWeatherApi = retrofit.create(IWeatherRequest::class.java)

        displayWeather()
    }

    private fun fetchData() {

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(getWeatherApi.getCurrentWeather(CITY, UNITS, KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onLoading() }
            .doFinally { onLoaded() }
            .subscribe(
                { WeatherModel -> getWeatherData(WeatherModel)},
                { onError(t = it)}
            )
        )
    }

    private fun getWeatherData(weather: WeatherModel) {

        val data = weather

        address.text = data.address
        updatedAt.text = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(data.updatedAt*1000))
        status.text = data.weatherDescription[0].status.capitalize()
        temp.text = data.main.temp +"°C"
        tempFielsLike.text = "Real Feel: " + data.main.tempFielsLike + "°C"
        sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(data.sys.sunrise*1000))
        sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(data.sys.sunset*1000))
        wind.text = data.wind.windspeed
        pressure.text = data.main.pressure
        humidity.text = data.main.humidity
    }

    private fun onError (t: Throwable) {
        mainContainer.visibility = View.GONE
        errorText.visibility = View.VISIBLE
        errorText.text = t.message
    }

    override fun displayWeather() {
        fetchData()
    }

    override fun onLoading() {
        loader.visibility = View.VISIBLE
    }

    override fun onLoaded() {
        mainContainer.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }
}