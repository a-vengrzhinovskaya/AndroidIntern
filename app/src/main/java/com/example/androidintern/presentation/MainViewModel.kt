package com.example.androidintern.presentation

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.database.WeatherDatabase
import com.example.androidintern.data.network.WeatherApi
import com.example.androidintern.data.toDomain
import com.example.androidintern.data.toSW
import com.example.androidintern.domain.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

private const val API_KEY = "8db7a14a267d03a0f3c870429391132e"
private const val CITY = "Kemerovo"
private const val UNITS = "metric"
private const val TIMBER_TAG = "weather"

class MainViewModel(
    private val database: WeatherDatabase,
    private val api: WeatherApi,
    private val sharedPRef: SharedPreferences
) : ViewModel() {
    private val _weathers = MutableLiveData<List<Weather>>()
    val weathers: LiveData<List<Weather>>
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
                loadWeatherFromNetwork()
            } catch (e: Exception) {
                Timber.tag(TIMBER_TAG).e(e)
                loadWeatherFromDatabase()
            }
        }
    }

    private suspend fun loadWeatherFromNetwork() {
        val weatherData = api.getWeather(CITY, API_KEY, UNITS)
        _weathers.postValue(weatherData.list.map { it.toDomain() })
        _cityName.postValue(weatherData.city.name)
        saveWeather()
    }

    private suspend fun loadWeatherFromDatabase() {
        val weather = database.weatherDao().getAll().map { it.toDomain() }
        _weathers.postValue(weather)
        _cityName.postValue(sharedPRef.getString(SHARED_PREF_NAME, CITY))
    }

    private suspend fun saveWeather() {
        _weathers.value?.let { weatherList ->
            database.weatherDao().update(weatherList.map { it.toSW() })
        }
        sharedPRef.edit {
            putString(SHARED_PREF_NAME, _cityName.value)
        }
    }


    companion object {
        const val SHARED_PREF_NAME = "city_name"

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            database: WeatherDatabase,
            api: WeatherApi,
            sharedPRef: SharedPreferences
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(database, api, sharedPRef) as T
                }
            }
    }
}