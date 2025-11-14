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
import ru.anlyashenko.fragmentapp.model.AuthRequest
import ru.anlyashenko.fragmentapp.model.User

class ProductRepository {
    private val json = Json { ignoreUnknownKeys = true }

    private val uiHandler = Handler(Looper.getMainLooper())

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(json.asConverterFactory(
            "application/json".toMediaType())
        )
        .build()

    private val mainApi = retrofit.create(MainApi::class.java)

    fun fetchProductById(id: Int, callback: (Result<Product>) -> Unit) {
        mainApi.getDataById(id).enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                val product = response.body()
                uiHandler.post {
                    if (response.isSuccessful && product != null) {
                        callback(Result.success(product))
                    } else {
                        callback(Result.failure(Exception("Ошибка: ${response.code()}")))
                    }
                }
            }

            override fun onFailure(
                call: Call<Product>,
                t: Throwable
            ) {
                uiHandler.post { callback(Result.failure(t)) }
            }

        })
    }

    fun fetchAllProducts(callback: (Result<List<Product>>) -> Unit) {
        mainApi.getAllData().enqueue(object : Callback<ProductResponse> {
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

    fun signIn(callback: (Result<String>) -> Unit) {
        val username = "emilys"
        val password = "emilyspass"

        mainApi.auth(AuthRequest(username, password,)).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User?>,
                response: Response<User?>
            ) {
                val user = response.body()
                uiHandler.post {
                    if (response.isSuccessful && user != null) {
                        callback(Result.success(user.accessToken))
                    } else {
                        callback(Result.failure(Exception("Ошибка: ${response.code()}")))
                    }
                }
            }

            override fun onFailure(
                call: Call<User?>,
                t: Throwable
            ) {
                uiHandler.post { callback(Result.failure(t)) }
            }

        })
    }

    fun fetchProductBySearch(token: String, name: String, callback: (Result<List<Product>>) -> Unit) {
        mainApi.getDataBySearchAuth("Bearer $token", name).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse?>,
                response: Response<ProductResponse?>
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

            override fun onFailure(
                call: Call<ProductResponse?>,
                t: Throwable
            ) {
                uiHandler.post { callback(Result.failure(t)) }
            }

        })

    }

}