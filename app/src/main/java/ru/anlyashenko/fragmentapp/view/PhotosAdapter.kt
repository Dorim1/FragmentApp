package ru.anlyashenko.fragmentapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.ItemPhotoBinding
import ru.anlyashenko.fragmentapp.model.MediaImage

class PhotosAdapter : ListAdapter<MediaImage, PhotosAdapter.ViewHolder>(PhotosDiffCallback()) {

    class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosAdapter.ViewHolder {
        val binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.ivPhoto.load(item.uri) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
        }
        holder.binding.tvTitlePhoto.text = item.name
    }


}