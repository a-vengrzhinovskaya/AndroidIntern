package com.example.androidintern.presentation

import androidx.lifecycle.LiveData
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
    private val _weathers = MutableLiveData<List<WeatherNW.WeatherData>>()
    val weathers: LiveData<List<WeatherNW.WeatherData>>
        get() = _weathers
    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String>
        get() = _cityName

    init {
        loadWeather()
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
                            _weathers.postValue(weatherData.list)
                            _cityName.postValue(weatherData.city.name)
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