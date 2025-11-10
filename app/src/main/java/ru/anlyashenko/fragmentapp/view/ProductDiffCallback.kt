package ru.anlyashenko.fragmentapp.view

import androidx.recyclerview.widget.DiffUtil
import ru.anlyashenko.fragmentapp.retrofit.Product

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem == newItem
    }
}