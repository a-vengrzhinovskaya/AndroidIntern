package com.example.androidintern.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidintern.databinding.ItemColdWeatherHolderBinding
import com.example.androidintern.databinding.ItemWarmWeatherHolderBinding
import com.example.androidintern.domain.Weather

private const val MIN_TEMPERATURE = 10
private const val TYPE_WARM = 1
private const val TYPE_COLD = 2

class WeatherAdapter :
    ListAdapter<Weather, RecyclerView.ViewHolder>(WeatherItemDiffCallback) {
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
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WarmWeatherViewHolder -> holder.bind(getItem(position))
            is ColdWeatherViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).temperature < MIN_TEMPERATURE) {
            TYPE_COLD
        } else {
            TYPE_WARM
        }
}

class WarmWeatherViewHolder(private val binding: ItemWarmWeatherHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: Weather) {
        binding.tvDate.text = weather.date
        Glide
            .with(binding.root)
            .load(getImageURL(weather.imageUrl))
            .into(binding.ivWeatherIcon)
        binding.tvPressure.text = weather.pressure.toString()
        binding.tvTemperature.text = weather.temperature.toString()
    }
}

class ColdWeatherViewHolder(private val binding: ItemColdWeatherHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: Weather) {
        binding.tvDate.text = weather.date
        Glide
            .with(binding.root)
            .load(getImageURL(weather.imageUrl))
            .into(binding.ivWeatherIcon)
        binding.tvPressure.text = weather.pressure.toString()
        binding.tvTemperature.text = weather.temperature.toString()
    }
}

private fun getImageURL(imageUrl: String) =
    "https://openweathermap.org/img/wn/$imageUrl.png"

object WeatherItemDiffCallback : DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(
        oldItem: Weather,
        newItem: Weather
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Weather,
        newItem: Weather
    ): Boolean =
        oldItem == newItem
}