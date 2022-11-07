package com.example.androidintern.data.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherSW(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "pressure") val pressure: Int,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "date") val date: String
)
