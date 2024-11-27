package com.novatech.pixabayphotoviewer.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.novatech.pixabayphotoviewer.databinding.ItemImageBinding
import com.novatech.pixabayphotoviewer.domain.model.Image

class ImageAdapter(private val onImageClicked: (Image) -> Unit) :
    ListAdapter<Image, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            binding.apply {
                Glide.with(root.context)
                    .load(image.previewURL)
                    .into(imageThumbnail)
                imageUploader.text = image.userName

                root.setOnClickListener { onImageClicked(image) }
            }
        }
    }
}

class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem
}