package com.example.androidintern

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.example.androidintern.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val REQUEST_CODE = 110
private const val FILTER_KEY = "filter_value"
private const val DEFAULT_VALUE = ""

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private val phonesFragment = NumberFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment()
        requestPermission()
        searchNumber()
        restoreFilterState()
    }

    private fun initFragment() {
        phonesFragment.fragmentSubmitList(getNumbers())
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentPhones, phonesFragment)
            .commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CALL_PHONE),
                REQUEST_CODE
            )
        }
    }

    private fun getNumbers(): List<PhoneNumber> {
        val itemType = object : TypeToken<List<PhoneNumber>>() {}.type
        return Gson().fromJson(jsonString.json, itemType)
    }

    private fun searchNumber() {
        binding.toolbar.etSearch.addTextChangedListener {
            val filteredNumbers = getNumbers().filter {
                it.name.contains(binding.toolbar.etSearch.text, true) ||
                        it.type.contains(binding.toolbar.etSearch.text, true) ||
                        it.phone.contains(binding.toolbar.etSearch.text)
            }
            phonesFragment.fragmentSubmitList(filteredNumbers)
            sharedPref.edit { putString(FILTER_KEY, binding.toolbar.etSearch.text.toString()) }
        }
    }

    private fun restoreFilterState() {
        sharedPref = getPreferences(MODE_PRIVATE)
        binding.toolbar.etSearch.setText(sharedPref.getString(FILTER_KEY, DEFAULT_VALUE))
    }
}