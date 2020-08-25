package com.example.forecast.View

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.forecast.Model.WeatherModel
import com.example.forecast.Presenter.IWeatherPresenter
import com.example.forecast.R
import com.example.forecast.Presenter.WeatherPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), IWeatherView {

    lateinit var address: TextView
    lateinit var updatedAt: TextView
    lateinit var status: TextView
    lateinit var temp: TextView
    lateinit var tempFielsLike: TextView
    lateinit var sunrise: TextView
    lateinit var sunset: TextView
    lateinit var wind: TextView
    lateinit var humidity: TextView

    lateinit var weatherPresenter: IWeatherPresenter

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
        humidity = findViewById(R.id.humidity)

        weatherPresenter = WeatherPresenter(this)
        weatherPresenter.fetchData()

        //Enable and set icon for options button
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onError (t: Throwable) {
        mainContainer.visibility = View.GONE
        errorText.visibility = View.VISIBLE
        errorText.text = t.message
    }

    override fun displayWeather(weatherModel: WeatherModel) {

        val data = weatherModel

        address.text = data.address
        updatedAt.text = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(data.updatedAt*1000))
        status.text = data.weatherDescription[0].status.capitalize()
        temp.text = data.main.temp +"°C"
        tempFielsLike.text = "Real Feel: " + data.main.tempFielsLike + "°C"
        sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(data.sys.sunrise*1000))
        sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(data.sys.sunset*1000))
        wind.text = data.wind.windspeed + " MPS"
        humidity.text = data.main.humidity + " %"
    }

    override fun onLoading() {
        mainContainer.visibility = View.GONE
        loader.visibility = View.VISIBLE
    }

    override fun onLoaded() {
        mainContainer.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }
}