package com.example.androidintern

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val FILTER_KEY = "filter_value"
private const val DEFAULT_VALUE = ""
private const val REQUEST_CODE = 110

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var currentNumber: PhoneNumber
    private val adapter = NumberAdapter { makePhoneCall(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        searchNumber()
        restoreFilterState()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makePhoneCall(currentNumber)
        } else {
            Toast.makeText(this,"Denied", Toast.LENGTH_SHORT).show()
        }
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

    private fun makePhoneCall(number: PhoneNumber) {
        currentNumber = number
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CALL_PHONE),
                REQUEST_CODE
            )
        } else {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number.phone, null))
            startActivity(intent)
        }
    }
}