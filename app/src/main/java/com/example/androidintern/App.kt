package com.example.androidintern

import android.app.Application
import androidx.room.Room
import com.example.androidintern.data.database.WeatherDatabase
import com.example.androidintern.data.network.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

private const val BASE_URL = "http://api.openweathermap.org/"

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        plantTimber()
        initWeatherApi()
        initWeatherDatabase()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initWeatherApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    private fun initWeatherDatabase() {
        weatherDB = Room.databaseBuilder(
            this,
            WeatherDatabase::class.java,
            "weather"
        ).build()
    }

    companion object {
        lateinit var weatherApi: WeatherApi
            private set
        lateinit var weatherDB: WeatherDatabase
            private set
    }
}