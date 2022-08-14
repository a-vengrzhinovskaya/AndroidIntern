package com.example.androidintern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.androidintern.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadImage()
    }

    private fun loadImage() {
        val url = intent.extras?.get(URL_KEY)
        Glide
            .with(this)
            .load(url)
            .into(binding.ivFullPhoto)
    }

    companion object {
        const val URL_KEY = "URL_KEY"
    }
}