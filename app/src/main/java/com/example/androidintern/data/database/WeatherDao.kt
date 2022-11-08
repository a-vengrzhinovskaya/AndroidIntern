package com.example.androidintern.data.database

import androidx.room.*
import com.example.androidintern.data.network.WeatherSW

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weathersw")
    fun getAll(): List<WeatherSW>

    @Insert
    fun insertAll(vararg: List<WeatherSW>)

    @Delete
    fun deleteAll(vararg: List<WeatherSW>)

    @Transaction
    fun deleteOldAndInsert(newWeather: List<WeatherSW>) {
        deleteAll(getAll())
        insertAll(newWeather)
    }
}