package com.example.androidintern

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidintern.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.IOException
import java.net.URL
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private const val CURRENT_URL =
    "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"
private const val REQUEST_CODE = 10

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Snackbar.make(binding.root, getString(R.string.snackbar), Snackbar.LENGTH_SHORT).show()
        }
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
        startActivityForResult(intent, REQUEST_CODE)
    }
}