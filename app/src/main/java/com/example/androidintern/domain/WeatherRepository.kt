package com.example.androidintern.domain

interface WeatherRepository {
    suspend fun getWeather(): List<Weather>

    suspend fun getCity(): City
}