package ru.anlyashenko.fragmentapp.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class Product (
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
)

@Serializable
data class ProductResponse(
    val products: List<Product>
)