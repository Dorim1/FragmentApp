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

class PostRepository {
    private val client = OkHttpClient()
    private val uiHandler = Handler(Looper.getMainLooper())

    fun fetchPost(id: Int, callback: (Result<Post>) -> Unit) {
        val url = "https://jsonplaceholder.typicode.com/posts/$id"
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
                        callback(Result.failure(Exception("Код: ${response.code}")))
                        return@post
                    }
                } else {
                    try {
                        val responseBody = response.body.string()
                        val jsonObject = JSONObject(responseBody)
                        val title = jsonObject.getString("title")
                        val id = jsonObject.getInt("id")
                        val userId = jsonObject.getInt("userId")
                        val body = jsonObject.getString("body")
                        val post = Post(id, userId, title, body)
                        uiHandler.post {
                            callback(Result.success(post))
                        }
                    } catch (e: JSONException) {
                        uiHandler.post { callback(Result.failure(e)) }
                    }
                }
            }
        })
    }
}