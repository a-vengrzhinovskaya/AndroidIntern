package com.example.androidintern.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidintern.App
import com.example.androidintern.data.network.WeatherSW
import com.example.androidintern.presentation.model.WeatherUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

private const val API_KEY = "8db7a14a267d03a0f3c870429391132e"
private const val CITY = "Kemerovo"
private const val UNITS = "metric"

class MainViewModel : ViewModel() {
    private val _weathers = MutableLiveData<List<WeatherUI.Weather>>()
    val weathers: LiveData<List<WeatherUI.Weather>>
        get() = _weathers
    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String>
        get() = _cityName

    init {
        loadWeather()
    }

    private fun loadWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherData = App.weatherApi.getWeather(CITY, API_KEY, UNITS)
                _weathers.postValue(
                    weatherData.list.map {
                        WeatherUI.Weather(
                            imageUrl = it.weather.first().icon,
                            pressure = it.main.pressure,
                            temperature = it.main.temp,
                            date = it.dtTxt
                        )
                    }
                )
                App.weatherDB.weatherDao().insertAll(
                    weatherData.list.map {
                        WeatherSW(
                            imageUrl = it.weather.first().icon,
                            pressure = it.main.pressure,
                            temperature = it.main.temp,
                            date = it.dtTxt
                        )
                    }
                )
                _cityName.postValue(weatherData.city.name)
            } catch (e: Exception) {
                Timber.tag("TAG").e(e)
                val weather = App.weatherDB.weatherDao().getAll().map {
                    WeatherUI.Weather(
                        imageUrl = it.imageUrl,
                        pressure = it.pressure,
                        temperature = it.temperature,
                        date = it.date
                    )
                }
                _weathers.postValue(weather)
                _cityName.postValue(CITY)
            }
        }
    }
}