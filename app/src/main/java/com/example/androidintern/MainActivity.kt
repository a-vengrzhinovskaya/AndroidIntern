package com.example.androidintern

import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), CellClickListener {
    private val callback = { text: String -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }

    private val adapter = ColorAdapter(emptyList(), this, callback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.setColors(resources.getStringArray(R.array.color_names).toList())
        initRV()
    }

    private fun initRV() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCellClickListener(text: String) {
        callback.invoke(text)
    }
}