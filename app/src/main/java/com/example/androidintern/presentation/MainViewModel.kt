package com.example.androidintern.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidintern.data.WeatherRepositoryImpl
import com.example.androidintern.domain.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: WeatherRepositoryImpl
) : ViewModel() {
    private val _weathers = MutableLiveData<List<Weather>>()
    val weathers: LiveData<List<Weather>>
        get() = _weathers
    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String>
        get() = _cityName

    init {
        loadForecast()
    }

    private fun loadForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            _weathers.postValue(repository.getWeather())
            _cityName.postValue(repository.getCity().name)
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