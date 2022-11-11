package com.example.androidintern.domain

data class Weather(
    val imageUrl: String,
    val pressure: Int,
    val temperature: Double,
    val date: String
)