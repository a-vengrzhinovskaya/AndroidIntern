package com.example.androidintern

import android.os.Bundle
import androidx.fragment.app.Fragment

class WeatherFragment : Fragment() {
    var weathers: WeatherNW? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}