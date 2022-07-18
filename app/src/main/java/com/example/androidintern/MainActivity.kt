package com.example.androidintern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import timber.log.Timber

val url =
    URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        getData(url)
    }

    private fun getData(url: URL) {
        thread {
            val connection = url.openConnection() as HttpURLConnection
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    val data = connection.inputStream.bufferedReader().readText()
                    Timber.i(data)

                } catch (exception: Exception) {
                    Timber.i(exception.message)
                } finally {
                    connection.disconnect()
                }
            } else {
                Timber.i("Error")
            }
        }
    }
}