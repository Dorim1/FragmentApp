package ru.anlyashenko.fragmentapp.view

import androidx.recyclerview.widget.DiffUtil
import ru.anlyashenko.fragmentapp.model.MediaImage

class PhotosDiffCallback : DiffUtil.ItemCallback<MediaImage>() {
    override fun areItemsTheSame(
        oldItem: MediaImage,
        newItem: MediaImage
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MediaImage,
        newItem: MediaImage
    ): Boolean {
        return oldItem == newItem
    }
}