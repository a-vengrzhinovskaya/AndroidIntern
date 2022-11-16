package com.example.androidintern.data.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.Transaction

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

    companion object {
        fun initWeatherDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                WeatherDatabase::class.java,
                "weather"
            ).build()
    }
}