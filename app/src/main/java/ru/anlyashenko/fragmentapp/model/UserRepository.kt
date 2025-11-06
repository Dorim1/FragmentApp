package ru.anlyashenko.fragmentapp.model

import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONException
import org.json.JSONObject

class UserRepository {
    private val client = OkHttpClient()
    private val uiHandler = Handler(Looper.getMainLooper())

    fun fetchUser(id: Int, callback: (Result<UserNet>) -> Unit) {
        val url = "https://jsonplaceholder.typicode.com/users/$id"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiHandler.post {
                    callback(Result.failure(e))
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    uiHandler.post {
                        callback(Result.failure(Exception("Код ошибки: ${response.code}")))
                        return@post
                    }
                } else {
                    try {
                        val resourceBody = response.body.string()
                        val jsonObject = JSONObject(resourceBody)
                        val id = jsonObject.getInt("id")
                        val name = jsonObject.getString("name")
                        val username = jsonObject.getString("username")
                        val email = jsonObject.getString("email")
                        val user = UserNet(id, name, username, email)
                        uiHandler.post {
                            callback(Result.success(user))
                        }
                    } catch (e: JSONException) {
                        uiHandler.post {
                            callback(Result.failure(e))
                        }
                    }
                }
            }

        })
    }
}