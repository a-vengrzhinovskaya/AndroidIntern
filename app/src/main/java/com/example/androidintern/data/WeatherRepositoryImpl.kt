package com.example.androidintern.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.androidintern.data.database.WeatherDatabase
import com.example.androidintern.data.network.WeatherApi
import com.example.androidintern.domain.City
import com.example.androidintern.domain.Weather
import com.example.androidintern.domain.WeatherRepository
import timber.log.Timber

private const val API_KEY = "8db7a14a267d03a0f3c870429391132e"
const val DEFAULT_CITY = "Kemerovo"
private const val UNITS = "metric"
const val TIMBER_TAG = "weather"

class WeatherRepositoryImpl(
    private val database: WeatherDatabase,
    private val api: WeatherApi,
    private val sharedPref: SharedPreferences
) : WeatherRepository {
    override suspend fun getWeather(): List<Weather> {
        var weather: List<Weather>
        try {
            weather = api.getWeather(DEFAULT_CITY, API_KEY, UNITS).list.map { it.toDomain() }
            database.weatherDao().update(weather.map { it.toSW() })
        } catch (e: Exception) {
            Timber.tag(TIMBER_TAG).e(e)
            weather = database.weatherDao().getAll().map { it.toDomain() }
        }
        return weather
    }

    override suspend fun getCity(): City {
        var cityName: String
        try {
            cityName = api.getWeather(DEFAULT_CITY, API_KEY, UNITS).city.name
            sharedPref.edit {
                putString(SHARED_PREF_NAME, cityName)
            }
        } catch (e: Exception) {
            cityName = sharedPref.getString(SHARED_PREF_NAME, DEFAULT_CITY).toString()
        }
        return City(cityName)
    }

    companion object {
        const val SHARED_PREF_NAME = "city_name"
    }
}