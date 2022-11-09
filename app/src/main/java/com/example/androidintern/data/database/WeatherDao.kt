package com.example.androidintern.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.androidintern.data.network.WeatherSW

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weathersw")
    suspend fun getAll(): List<WeatherSW>

    @Insert
    suspend fun insertAll(vararg: List<WeatherSW>)

    @Query("DELETE FROM weathersw")
    suspend fun deleteAll()

    @Transaction
    suspend fun update(newWeather: List<WeatherSW>) {
        deleteAll()
        insertAll(newWeather)
    }
}