package ru.anlyashenko.fragmentapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.anlyashenko.fragmentapp.databinding.ItemPostBinding
import ru.anlyashenko.fragmentapp.retrofit.Product

class ProductAdapter : androidx.recyclerview.widget.ListAdapter<Product, ProductAdapter.ViewHolder>(
    ProductDiffCallback()
) {

    class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.binding.tvTitle.text = product.title
    }

}