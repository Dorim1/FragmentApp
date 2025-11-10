package ru.anlyashenko.fragmentapp.retrofit

import android.os.Handler
import android.os.Looper
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ProductRepository {
    private val json = Json { ignoreUnknownKeys = true }

    private val uiHandler = Handler(Looper.getMainLooper())

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(json.asConverterFactory(
            "application/json".toMediaType())
        )
        .build()

    private val productApi = retrofit.create(ProductApi::class.java)

    fun fetchAllProducts(callback: (Result<List<Product>>) -> Unit) {
        productApi.getData().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                val productsList = response.body()?.products
                uiHandler.post {
                    if (response.isSuccessful && productsList != null) {
                        callback(Result.success(productsList))
                    } else {
                        callback(Result.failure(Exception("Ошибка: ${response.code()}")))
                    }
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                uiHandler.post { callback(Result.failure(t)) }
            }
        })
    }

}