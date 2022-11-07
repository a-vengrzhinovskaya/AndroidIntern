package com.example.androidintern.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidintern.data.network.WeatherSW

@Database(entities = [WeatherSW::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}