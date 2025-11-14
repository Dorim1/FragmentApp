package ru.anlyashenko.fragmentapp.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.anlyashenko.fragmentapp.model.AuthRequest
import ru.anlyashenko.fragmentapp.model.User

interface MainApi {
    @GET("products")
    fun getAllData(): Call<ProductResponse>

    @GET("products/{id}")
    fun getDataById(@Path("id") id: Int): Call<Product>

    @POST("auth/login")
    fun auth(@Body authRequest: AuthRequest): Call<User>


    @GET("auth/products/search")
    fun getDataBySearchAuth(
        @Header("Authorization") token: String,
        @Query("q") name: String
    ): Call<ProductResponse>


    @GET("auth/products/search")
    fun getDataBySearch(
        @Query("q") name: String
    ): Call<ProductResponse>
}