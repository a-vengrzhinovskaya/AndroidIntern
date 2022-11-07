package com.example.androidintern.presentation.model

sealed class WeatherUI {
    data class Weather(
        val imageUrl: String,
        val pressure: Int,
        val temperature: Double,
        val date: String
    )
}