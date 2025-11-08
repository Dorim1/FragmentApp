package ru.anlyashenko.fragmentapp.model

import android.os.Handler
import android.os.Looper
import android.util.Log.e
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PostRepository {
    private val client = OkHttpClient()
    private val uiHandler = Handler(Looper.getMainLooper())
    private val gson = Gson()

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
                response.use {
                    if (!it.isSuccessful) {
                        uiHandler.post {
                            callback(Result.failure(Exception("Код: ${it.code}")))
                        }
                    } else {
                        try {
                            // Без GSON
//                        val responseBody = response.body.string()
//                        val jsonArray = JSONArray(responseBody)
//                        val postList = mutableListOf<Post>()
//
//                        for (i in 0 until jsonArray.length()) {
//                            val jsonObject = jsonArray.getJSONObject(i)
//                            val id = jsonObject.getInt("id")
//                            val title = jsonObject.getString("title")
//                            val userId = jsonObject.getInt("userId")
//                            val body = jsonObject.getString("body")
//                            val post = Post(id, userId, title, body)
//                            postList.add(post)
//                          }

                            // С GSON
                            val responseBody = it.body.string()
                            val listType = object : TypeToken<List<Post>>() {}.type
                            val postList: List<Post> = gson.fromJson(responseBody, listType)
                            uiHandler.post {
                                callback(Result.success(postList))
                            }
                        } catch (e: JsonSyntaxException) {
                            uiHandler.post { callback(Result.failure(e)) }
                        }
                    }
                }
            }
        })
    }

    fun deletePost(postId: Int, callback: (Result<Unit>) -> Unit) {
        val url = "https://jsonplaceholder.typicode.com/posts/$postId"
        val request = Request.Builder()
            .url(url)
            .delete()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiHandler.post { callback(Result.failure(e)) }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        uiHandler.post { callback(Result.success(Unit)) }
                    } else {
                        uiHandler.post { callback(Result.failure(Exception("Код: ${it.code}"))) }
                    }
                }
            }
        })
    }

    fun updatePost(post: Post, callback: (Result<Post>) -> Unit) {
        val url = "https://jsonplaceholder.typicode.com/posts/${post.id}"
        val jsonString = gson.toJson(post)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonString.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiHandler.post { callback(Result.failure(e)) }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                       if (!it.isSuccessful) {
                           uiHandler.post { callback(Result.failure(Exception("Код: ${it.code}"))) }
                       } else {
                           try {
                               val responseBody = it.body.string()
                               val updatePost: Post = gson.fromJson(responseBody, Post::class.java)
                               uiHandler.post { callback(Result.success(updatePost)) }
                           }catch (e: JsonSyntaxException) {
                               uiHandler.post { callback(Result.failure(e)) }
                           }
                       }
                }
            }

        })
    }

}
