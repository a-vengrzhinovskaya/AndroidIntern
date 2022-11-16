package com.example.androidintern.domain

sealed class WeatherType {
    data class FromNW(val weatherData: WeatherData) : WeatherType()
    data class FromDB(val weatherData: WeatherData) : WeatherType()
}