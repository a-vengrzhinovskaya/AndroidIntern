package com.example.androidintern

import android.app.Application
import androidx.room.Room
import com.example.androidintern.data.WeatherRepositoryImpl
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
        initRepository()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initWeatherApi(): WeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WeatherApi::class.java)
    }

    private fun initWeatherDatabase() =
        Room.databaseBuilder(
            this,
            WeatherDatabase::class.java,
            "weather"
        ).build()

    private fun initRepository() {
        weatherRepository = WeatherRepositoryImpl(
            database = initWeatherDatabase(),
            api = initWeatherApi(),
            sharedPref = getSharedPreferences(
                WeatherRepositoryImpl.SHARED_PREF_NAME,
                MODE_PRIVATE
            )
        )
    }

    companion object {
        lateinit var weatherRepository: WeatherRepositoryImpl
            private set
    }
}