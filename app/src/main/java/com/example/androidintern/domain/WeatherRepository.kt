package com.example.androidintern.domain

interface WeatherRepository {
    suspend fun getWeather(): WeatherType
}