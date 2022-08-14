package com.example.androidintern

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidintern.databinding.ItemPhotoHolderBinding

class PhotoAdapter(
    private var photos: List<Picture.Photos.Photo>,
    private val callback: (color: String) -> Unit
) :
    RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoHolderBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position], callback)
    }

    override fun getItemCount() = photos.size

    fun setContent(newPhotos: List<Picture.Photos.Photo>) {
        photos = newPhotos
        notifyDataSetChanged()
    }
}

class PhotoViewHolder(private val binding: ItemPhotoHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Picture.Photos.Photo, callback: (color: String) -> Unit) {
        val url =
            "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg"
        Glide
            .with(binding.root)
            .load(url)
            .into(binding.ivPhoto)

        binding.ivPhoto.setOnClickListener{
            callback(url)
        }
    }
}