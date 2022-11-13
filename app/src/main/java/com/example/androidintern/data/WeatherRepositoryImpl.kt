package com.example.androidintern.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.androidintern.data.database.WeatherDatabase
import com.example.androidintern.data.network.WeatherApi
import com.example.androidintern.domain.City
import com.example.androidintern.domain.WeatherData
import com.example.androidintern.domain.WeatherRepository
import timber.log.Timber

private const val API_KEY = "8db7a14a267d03a0f3c870429391132e"
private const val DEFAULT_CITY = "Kemerovo"
private const val UNITS = "metric"
private const val TIMBER_TAG = "weather"

class WeatherRepositoryImpl(
    private val database: WeatherDatabase,
    private val api: WeatherApi,
    private val sharedPref: SharedPreferences
) : WeatherRepository {
    override suspend fun getWeather(): WeatherData =
        try {
            loadWeatherFromNetwork()
        } catch (e: Exception) {
            Timber.tag(TIMBER_TAG).e(e)
            loadWeatherFromDatabase()
        }

    private suspend fun loadWeatherFromNetwork(): WeatherData {
        val weathers = api.getWeather(DEFAULT_CITY, API_KEY, UNITS)
        database.weatherDao().update(weathers.list.map { it.toSW() })
        sharedPref.edit {
            putString(SHARED_PREF_NAME, weathers.city.name)
        }
        return WeatherData(weathers.list.map { it.toDomain() }, City(weathers.city.name))
    }

    private suspend fun loadWeatherFromDatabase(): WeatherData {
        val weatherList = database.weatherDao().getAll().map { it.toDomain() }
        val cityName = sharedPref.getString(SHARED_PREF_NAME, DEFAULT_CITY).toString()
        return WeatherData(weatherList, City(cityName))
    }

    companion object {
        const val SHARED_PREF_NAME = "city_name"
    }
}