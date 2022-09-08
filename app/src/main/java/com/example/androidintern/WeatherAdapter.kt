package com.example.androidintern

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidintern.databinding.ItemWeatherHolderBinding

private const val ICON_URL = "https://openweathermap.org/img/wn/"

class NumberAdapter :
    ListAdapter<WeatherNW.WeatherData, WeatherViewHolder>(WeatherItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            ItemWeatherHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WeatherViewHolder(private val binding: ItemWeatherHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherNW.WeatherData) {
        binding.tvDate.text = weather.dtTxt
        Glide
            .with(binding.root)
            .load("$ICON_URL${weather.weather.first().icon}.png")
            .into(binding.ivWeatherIcon)
        binding.tvPressure.text = weather.main.pressure.toString()
        binding.tvTemperature.text = weather.main.temp.toString()
    }
}

object WeatherItemDiffCallback : DiffUtil.ItemCallback<WeatherNW.WeatherData>() {
    override fun areItemsTheSame(oldItem: WeatherNW.WeatherData, newItem: WeatherNW.WeatherData): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: WeatherNW.WeatherData, newItem: WeatherNW.WeatherData): Boolean =
        oldItem == newItem
}