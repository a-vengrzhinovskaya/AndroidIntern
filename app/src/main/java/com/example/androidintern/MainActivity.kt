package com.example.androidintern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

private const val API_KEY = "8db7a14a267d03a0f3c870429391132e"
private const val CITY = "Kemerovo"
private const val UNITS = "metric"
private const val BASE_URL = "https://api.openweathermap.org/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val weatherApi by lazy {
        createWeatherApi()
    }
    private val adapter = NumberAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        if (WeatherStore.weathers == null) {
            getWeather()
        } else {
            restoreWeather()
        }
    }

    private fun restoreWeather() {
        adapter.submitList(WeatherStore.weathers?.list)
        initToolbar(WeatherStore.weathers?.city?.name.toString())
    }

    private fun getWeather() {
        weatherApi.getWeather(CITY, API_KEY, UNITS).enqueue(
            object : Callback<WeatherNW> {
                override fun onResponse(
                    call: Call<WeatherNW>,
                    response: Response<WeatherNW>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { weatherData ->
                            adapter.submitList(weatherData.list)
                            WeatherStore.weathers = weatherData
                            initToolbar(weatherData.city.name)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherNW>, t: Throwable) {
                    Timber.d(t)
                }
            }
        )
    }

    private fun createWeatherApi(): WeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
        return retrofit.create(WeatherApi::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return okHttpClient
    }

    private fun initToolbar(cityName: String) {
        binding.toolbar.tvCityName.text = cityName
    }

    private fun initRecyclerView() {
        binding.rvWeather.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvWeather.adapter = adapter
    }
}