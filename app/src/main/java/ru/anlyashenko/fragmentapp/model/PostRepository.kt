package ru.anlyashenko.fragmentapp.model

import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PostRepository {
    private val client = OkHttpClient()
    private val uiHandler = Handler(Looper.getMainLooper())

    fun fetchAllPosts(callback: (Result<List<Post>>) -> Unit) {
        val url = "https://jsonplaceholder.typicode.com/posts"
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
                        val jsonArray = JSONArray(responseBody)
                        val postList = mutableListOf<Post>()

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val id = jsonObject.getInt("id")
                            val title = jsonObject.getString("title")
                            val userId = jsonObject.getInt("userId")
                            val body = jsonObject.getString("body")
                            val post = Post(id, userId, title, body)
                            postList.add(post)
                        }
                        uiHandler.post {
                            callback(Result.success(postList))
                        }
                    } catch (e: JSONException) {
                        uiHandler.post { callback(Result.failure(e)) }
                    }
                }
            }
        })
    }
}