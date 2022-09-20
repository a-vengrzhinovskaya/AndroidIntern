package com.example.androidintern

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.databinding.FragmentNumberBinding

class NumberFragment : Fragment() {
    private lateinit var binding: FragmentNumberBinding
    private val adapter = NumberAdapter { makePhoneCall(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNumberBinding.inflate(layoutInflater)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvPhoneNumbers.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
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

    fun fragmentSubmitList(phones: List<PhoneNumber>) {
        adapter.submitList(phones)
    }
}