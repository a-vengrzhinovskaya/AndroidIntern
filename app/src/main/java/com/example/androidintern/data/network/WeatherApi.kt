package com.example.androidintern.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/forecast")
    suspend fun getWeather(
        @Query("q")
        city: String,
        @Query("appid")
        key: String,
        @Query("units")
        unit: String
    ): WeatherNW
}