package com.example.androidintern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ColorAdapter(
    private var colorNames: List<String>,
    private val cellClick: CellClickListener,
    private val callback: (color: String) -> Unit
) :
    RecyclerView.Adapter<ColorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(item)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colorNames[position], callback, cellClick)
    }

    override fun getItemCount() = colorNames.size

    fun setColors(newColors: List<String>) {
        colorNames = newColors
        notifyDataSetChanged()
    }
}

class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textView = view.findViewById<TextView>(R.id.recycler_text_view)

    fun bind(item: String, callback: (color: String) -> Unit, cellClick: CellClickListener) {
        textView.text = item
        textView.setOnClickListener {
            cellClick.onCellClickListener(item)
        }
    }
}