package ru.anlyashenko.fragmentapp.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    fun getData(): Call<ProductResponse>
}