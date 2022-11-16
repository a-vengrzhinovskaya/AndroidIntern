package com.example.androidintern.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.App
import com.example.androidintern.R
import com.example.androidintern.databinding.ActivityMainBinding
import com.example.androidintern.domain.Weather
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter =
        WeatherAdapter { weather: Weather ->
            shareWeather(weather)
        }
    private val viewModel: MainViewModel by viewModels() {
        MainViewModel.provideFactory(App.weatherRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        observeWeather()
    }

    private fun initToolbar(cityName: String) {
        binding.toolbar.tvCityName.text = cityName
    }

    private fun initRecyclerView() {
        binding.rvWeather.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvWeather.adapter = adapter
    }

    private fun shareWeather(weather: Weather) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, createWeatherInfoString(weather))
            putExtra(Intent.EXTRA_SUBJECT, viewModel.weatherData.value?.city?.name)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun createWeatherInfoString(weather: Weather) =
        "date:${weather.date}\ntemperature:${weather.temperature}"

    private fun observeWeather() {
        viewModel.weatherData.observe(this) { weathers ->
            adapter.submitList(weathers.weatherList)
            initToolbar(weathers.city.name)
        }
        viewModel.loadEvent.observe(this) {
            it.getContentIfNotHandled()?.let {
                Snackbar.make(
                    binding.root,
                    getString(R.string.snack_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}