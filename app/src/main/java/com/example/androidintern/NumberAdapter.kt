package com.example.androidintern

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidintern.databinding.ItemNumberHolderBinding

class NumberAdapter :
    ListAdapter<PhoneNumber, PhoneNumberViewHolder>(PhoneNumberItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneNumberViewHolder {
        val binding =
            ItemNumberHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhoneNumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneNumberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PhoneNumberViewHolder(private val binding: ItemNumberHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(number: PhoneNumber) {
        binding.tvName.text = number.name
        binding.tvPhone.text = number.phone
        binding.tvType.text = number.type
    }
}

object PhoneNumberItemDiffCallback : DiffUtil.ItemCallback<PhoneNumber>() {
    override fun areItemsTheSame(oldItem: PhoneNumber, newItem: PhoneNumber): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: PhoneNumber, newItem: PhoneNumber): Boolean =
        oldItem == newItem
}