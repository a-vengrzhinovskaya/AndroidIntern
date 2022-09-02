package com.example.androidintern

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val FILTER_KEY = "filter_value"
private const val DEFAULT_VALUE = ""

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private val adapter = NumberAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        searchNumber()
        restoreFilterState()
    }

    private fun getNumbers(): List<PhoneNumber> {
        val itemType = object : TypeToken<List<PhoneNumber>>() {}.type
        return Gson().fromJson(jsonString.json, itemType)
    }

    private fun initRecyclerView() {
        binding.rvPhoneNumbers.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.submitList(getNumbers())
        binding.rvPhoneNumbers.adapter = adapter
    }

    private fun searchNumber() {
        binding.toolbar.etSearch.addTextChangedListener {
            val filteredNumbers = getNumbers().filter {
                it.name.contains(binding.toolbar.etSearch.text, true) ||
                        it.type.contains(binding.toolbar.etSearch.text, true) ||
                        it.phone.contains(binding.toolbar.etSearch.text)
            }
            adapter.submitList(filteredNumbers)
            sharedPref.edit { putString(FILTER_KEY, binding.toolbar.etSearch.text.toString()) }
        }
    }

    private fun restoreFilterState() {
        sharedPref = getPreferences(MODE_PRIVATE)
        binding.toolbar.etSearch.setText(sharedPref.getString(FILTER_KEY, DEFAULT_VALUE))
    }
}