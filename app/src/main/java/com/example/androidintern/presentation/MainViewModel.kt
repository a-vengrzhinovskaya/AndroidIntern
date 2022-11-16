package com.example.androidintern.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.WeatherRepositoryImpl
import com.example.androidintern.domain.WeatherData
import com.example.androidintern.domain.WeatherType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: WeatherRepositoryImpl
) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData
    private val _loadEvent = MutableLiveData<Event<Unit>>()
    val loadEvent: LiveData<Event<Unit>>
        get() = _loadEvent

    init {
        loadForecast()
    }

    private fun loadForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherData = repository.getWeather()
            when (weatherData) {
                is WeatherType.FromNW -> {
                    _weatherData.postValue(weatherData.weatherData)
                }
                is WeatherType.FromDB -> {
                    _loadEvent.postValue(Event(Unit))
                    _weatherData.postValue(weatherData.weatherData)
                }
            }

        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            repository: WeatherRepositoryImpl
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(repository) as T
                }
            }
    }
}