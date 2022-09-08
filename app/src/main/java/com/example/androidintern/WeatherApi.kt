package com.example.androidintern

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/forecast")
    fun getWeather(
        @Query("q")
        city: String,
        @Query("appid")
        key: String,
        @Query("units")
        unit: String
    ): Call<WeatherNW>
}