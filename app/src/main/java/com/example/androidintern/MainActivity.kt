package com.example.androidintern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button_edit)
        val edit = findViewById<EditText>(R.id.edit_text)

        button.setOnClickListener {
            Log.i("text_info", edit.text.toString())
            Timber.i(edit.text.toString())
        }
    }
}