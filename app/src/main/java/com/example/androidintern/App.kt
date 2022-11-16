package com.example.androidintern

import android.app.Application
import com.example.androidintern.data.WeatherRepositoryImpl
import com.example.androidintern.data.database.WeatherDao
import com.example.androidintern.data.network.WeatherApi
import timber.log.Timber

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

    private fun initRepository() {
        weatherRepository = WeatherRepositoryImpl(
            database = WeatherDao.initWeatherDatabase(context = this),
            api = WeatherApi.initWeatherApi(),
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