package com.example.androidintern.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.androidintern.data.database.WeatherDatabase
import com.example.androidintern.data.network.WeatherApi
import com.example.androidintern.domain.City
import com.example.androidintern.domain.WeatherData
import com.example.androidintern.domain.WeatherRepository
import com.example.androidintern.domain.WeatherType
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
    override suspend fun getWeather(): WeatherType =
        try {
            loadWeatherFromNetwork()
        } catch (e: Exception) {
            Timber.tag(TIMBER_TAG).e(e)
            loadWeatherFromDatabase()
        }

    private suspend fun loadWeatherFromNetwork(): WeatherType.FromNW {
        val weathers = api.getWeather(DEFAULT_CITY, API_KEY, UNITS)
        database.weatherDao().update(weathers.list.map { it.toSW() })
        sharedPref.edit {
            putString(SHARED_PREF_NAME, weathers.city.name)
        }
        val weatherData = WeatherData(weathers.list.map { it.toDomain() }, City(weathers.city.name))
        return WeatherType.FromNW(weatherData)
    }

    private suspend fun loadWeatherFromDatabase(): WeatherType.FromDB {
        val weathers = database.weatherDao().getAll().map { it.toDomain() }
        val cityName = sharedPref.getString(SHARED_PREF_NAME, DEFAULT_CITY).toString()
        val weatherData = WeatherData(weathers, City(cityName))
        return WeatherType.FromDB(weatherData)
    }

    companion object {
        const val SHARED_PREF_NAME = "city_name"
    }
}