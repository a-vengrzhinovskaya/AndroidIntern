package com.example.androidintern.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.App
import com.example.androidintern.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = WeatherAdapter()
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

    private fun observeWeather() {
        viewModel.weathers.observe(this) { weathers ->
            adapter.submitList(weathers)
        }
        viewModel.city.observe(this) { city ->
            initToolbar(city.name)
        }
    }
}