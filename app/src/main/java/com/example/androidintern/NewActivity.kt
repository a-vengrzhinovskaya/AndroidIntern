package com.example.androidintern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.androidintern.databinding.ActivityNewBinding

private const val IMAGE_URL = "https://bigpicture.ru/wp-content/uploads/2012/04/2184.jpg"

class NewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide
            .with(this)
            .load(IMAGE_URL)
            .into(binding.ivGlide)
    }
}