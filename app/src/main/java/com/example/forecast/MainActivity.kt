package com.example.forecast

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.forecast.Model.WeatherModel
import com.example.forecast.Retrofit.IWeatherRequest
import com.example.forecast.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

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
        val getWeatherApi = retrofit.create(IWeatherRequest::class.java)

        val getWeather = getWeatherApi.getCurrentWeather(CITY, UNITS, KEY)

        getWeather.enqueue(object: Callback<WeatherModel> {
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                            t.message,
                            Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {

                val jsonObj = response.body()

                address.text = jsonObj?.address.toString()
                updatedAt.text = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(jsonObj!!.updatedAt*1000))
                status.text = jsonObj.weatherDescription[0].status.capitalize()
                temp.text = jsonObj.main.temp + "°C"
                tempFielsLike.text = "Fiels Like: " + jsonObj.main.tempFielsLike +"°C"
                sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(jsonObj.sys.sunrise*1000))
                sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(jsonObj.sys.sunset*1000))
                wind.text = jsonObj.wind.windspeed
                pressure.text = jsonObj.main.pressure
                humidity.text = jsonObj.main.humidity
            }
        })
    }
}