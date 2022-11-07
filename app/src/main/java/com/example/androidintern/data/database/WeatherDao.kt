package com.example.androidintern.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.androidintern.data.network.WeatherSW

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weathersw")
    fun getAll(): List<WeatherSW>

    @Insert
    fun insertAll(vararg: List<WeatherSW>)
}