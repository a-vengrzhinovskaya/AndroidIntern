package com.example.androidintern.data

import com.example.androidintern.data.database.WeatherSW
import com.example.androidintern.data.network.WeatherNW
import com.example.androidintern.domain.Weather

fun WeatherNW.WeatherData.toSW() = WeatherSW(
    imageUrl = weather.first().icon,
    pressure = main.pressure,
    temperature = main.temp,
    date = dtTxt
)

fun WeatherNW.WeatherData.toDomain() = Weather(
    imageUrl = weather.first().icon,
    pressure = main.pressure,
    temperature = main.temp,
    date = dtTxt
)

fun WeatherSW.toDomain() = Weather(
    imageUrl = imageUrl,
    pressure = pressure,
    temperature = temperature,
    date = date
)

fun Weather.toSW() = WeatherSW(
    imageUrl = imageUrl,
    pressure = pressure,
    temperature = temperature,
    date = date
)