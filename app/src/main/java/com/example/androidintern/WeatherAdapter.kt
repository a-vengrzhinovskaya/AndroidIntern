package com.example.androidintern

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidintern.databinding.ItemColdWeatherHolderBinding
import com.example.androidintern.databinding.ItemWarmWeatherHolderBinding

private const val ICON_URL = "https://openweathermap.org/img/wn/"
private const val TEMPERATURE = 10
private const val TYPE_WARM = 1
private const val TYPE_COLD = 2

class NumberAdapter :
    ListAdapter<WeatherNW.WeatherData, RecyclerView.ViewHolder>(WeatherItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_WARM -> {
                val binding =
                    ItemWarmWeatherHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return WarmWeatherViewHolder(binding)
            }
            TYPE_COLD -> {
                val binding =
                    ItemColdWeatherHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ColdWeatherViewHolder(binding)
            }
            else -> throw Exception()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WarmWeatherViewHolder -> holder.bind(getItem(position))
            is ColdWeatherViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).main.temp < TEMPERATURE) {
            TYPE_COLD
        } else {
            TYPE_WARM
        }
}

class WarmWeatherViewHolder(private val binding: ItemWarmWeatherHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherNW.WeatherData) {
        binding.tvDate.text = weather.dtTxt
        Glide
            .with(binding.root)
            .load(getImageURL(weather))
            .into(binding.ivWeatherIcon)
        binding.tvPressure.text = weather.main.pressure.toString()
        binding.tvTemperature.text = weather.main.temp.toString()
    }
}

class ColdWeatherViewHolder(private val binding: ItemColdWeatherHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: WeatherNW.WeatherData) {
        binding.tvDate.text = weather.dtTxt
        Glide
            .with(binding.root)
            .load(getImageURL(weather))
            .into(binding.ivWeatherIcon)
        binding.tvPressure.text = weather.main.pressure.toString()
        binding.tvTemperature.text = weather.main.temp.toString()
    }
}

private fun getImageURL(weather: WeatherNW.WeatherData) =
    "$ICON_URL${weather.weather.first().icon}.png"

object WeatherItemDiffCallback : DiffUtil.ItemCallback<WeatherNW.WeatherData>() {
    override fun areItemsTheSame(
        oldItem: WeatherNW.WeatherData,
        newItem: WeatherNW.WeatherData
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: WeatherNW.WeatherData,
        newItem: WeatherNW.WeatherData
    ): Boolean =
        oldItem == newItem
}