package com.example.androidintern.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidintern.App
import com.example.androidintern.data.WeatherNW
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

private const val API_KEY = "8db7a14a267d03a0f3c870429391132e"
private const val CITY = "Kemerovo"
private const val UNITS = "metric"

class WeatherViewModel : ViewModel() {
    private var weathers = MutableLiveData<WeatherNW>()

    fun getWeather(): MutableLiveData<WeatherNW> {
        loadWeather()
        return weathers
    }

    private fun loadWeather() {
        App.weatherApi.getWeather(CITY, API_KEY, UNITS).enqueue(
            object : Callback<WeatherNW> {
                override fun onResponse(
                    call: Call<WeatherNW>,
                    response: Response<WeatherNW>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { weatherData ->
                            weathers.postValue(weatherData)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherNW>, t: Throwable) {
                    Timber.d(t)
                }
            }
        )
    }
}