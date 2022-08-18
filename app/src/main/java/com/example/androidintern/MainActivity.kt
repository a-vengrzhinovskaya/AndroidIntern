package com.example.androidintern

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidintern.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

private const val CURRENT_URL =
    "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val onPhotoClick = { photo: Picture.Photos.Photo ->
        openImage(photo)
    }

    private val adapter = PhotoAdapter(emptyList(), onPhotoClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPhotos()
        initRV()
    }

    private fun getPhotos() {
        val url = URL(CURRENT_URL)
        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("http", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val pictures = Gson().fromJson(response.body?.string(), Picture::class.java)
                        runOnUiThread {
                            adapter.setContent(pictures.photos.photo)
                        }
                    }
                }
            }
        )
    }

    private fun initRV() {
        binding.rvPhotos.layoutManager = GridLayoutManager(this, 2)
        binding.rvPhotos.adapter = adapter
    }

    private fun openImage(photo: Picture.Photos.Photo) {
        val url =
            "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_z.jpg"
        val intent = Intent(this, PhotoActivity::class.java).apply {
            putExtra(PhotoActivity.URL_KEY, url)
        }
        startActivity(intent)
    }
}