package ru.anlyashenko.fragmentapp.retrofit

import android.os.Handler
import android.os.Looper
import com.squareup.picasso.Picasso
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.anlyashenko.fragmentapp.model.AuthRequest
import ru.anlyashenko.fragmentapp.model.User
import kotlin.random.Random

class AuthRepository {

    private val json = Json { ignoreUnknownKeys = true }

    private val uiHandler = Handler(Looper.getMainLooper())

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(json.asConverterFactory(
            "application/json".toMediaType())
        )
        .build()

    private val mainApi = retrofit.create(MainApi::class.java)

    fun signIn(username: String, password: String, callback: (Result<User>) -> Unit) {
        mainApi.auth(AuthRequest(username, password)).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                val user = response.body()
                uiHandler.post {
                    if (response.isSuccessful && user != null) {
                        callback(Result.success(user))
                    } else {
                        uiHandler.post { callback(Result.failure(Exception("Ошибка: ${response.code()}"))) }
                    }
                }
            }

            override fun onFailure(
                call: Call<User>,
                t: Throwable
            ) {
                uiHandler.post { callback(Result.failure(t)) }
            }

        })
    }


}