package com.example.androidintern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ColorAdapter(private val colorNames: List<String>) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    class ColorViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textView = item.findViewById<TextView>(R.id.recycler_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(item)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.textView.text = colorNames[position]
    }

    override fun getItemCount() = colorNames.size
}
