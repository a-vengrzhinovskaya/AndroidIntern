package com.example.androidintern

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidintern.databinding.ItemNumberHolderBinding

class NumberAdapter(private var numbers: List<PhoneNumber>) :
    RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemNumberHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(numbers[position])
    }

    override fun getItemCount() = numbers.size

    fun setContent(newNumbers: List<PhoneNumber>) {
        numbers = newNumbers
        notifyDataSetChanged()
    }
}

class PhotoViewHolder(private val binding: ItemNumberHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(number: PhoneNumber) {
        binding.tvName.text = number.name
        binding.tvPhone.text = number.phone
        binding.tvType.text = number.type
    }
}