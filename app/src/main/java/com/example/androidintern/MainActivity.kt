package com.example.androidintern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.text_view)
        val checkBox = findViewById<CheckBox>(R.id.checkbox)
        val editText = findViewById<EditText>(R.id.edit_text)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        button.setOnClickListener {
            if (checkBox.isChecked) {
                textView.text = editText.text
                progressBar.progress += 10

                if (progressBar.progress == progressBar.max) {
                    progressBar.progress = 0
                }
            }
        }
    }
}