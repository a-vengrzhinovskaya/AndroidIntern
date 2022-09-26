package com.example.androidintern

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.databinding.FragmentNumberBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val FILTER_KEY = "filter_value"
private const val DEFAULT_VALUE = ""

class NumberFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences
    private var _binding: FragmentNumberBinding? = null
    private val binding get() = _binding!!
    private val adapter = NumberAdapter { makePhoneCall(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNumberBinding.inflate(layoutInflater)

        initRecyclerView()
        searchNumber()
        restoreFilterState()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvPhoneNumbers.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        adapter.submitList(getNumbers())
        binding.rvPhoneNumbers.adapter = adapter
    }

    private fun makePhoneCall(number: PhoneNumber) {
        if (ContextCompat.checkSelfPermission(
                binding.root.context,
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number.phone, null))
            startActivity(intent)
        } else {
            Toast.makeText(binding.root.context, "Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNumbers(): List<PhoneNumber> {
        val itemType = object : TypeToken<List<PhoneNumber>>() {}.type
        return Gson().fromJson(WeatherJson.string, itemType)
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
        sharedPref =
            requireActivity().getSharedPreferences(FILTER_KEY, AppCompatActivity.MODE_PRIVATE)
        binding.toolbar.etSearch.setText(sharedPref.getString(FILTER_KEY, DEFAULT_VALUE))
    }
}