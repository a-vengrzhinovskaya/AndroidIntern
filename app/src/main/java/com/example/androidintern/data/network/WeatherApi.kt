package com.example.androidintern.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://api.openweathermap.org/"

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

    companion object {
        fun initWeatherApi(): WeatherApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(WeatherApi::class.java)
        }
    }
}