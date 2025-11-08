package ru.anlyashenko.fragmentapp.viewModel

import android.app.usage.UsageEvents
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.anlyashenko.fragmentapp.model.Post
import ru.anlyashenko.fragmentapp.model.PostRepository
import kotlin.math.PI

class PostViewModel : ViewModel() {
    private val repository = PostRepository()
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _deleteSuccess = MutableLiveData<String>()
    val deleteSuccess: LiveData<String> = _deleteSuccess
    private val _updateSuccess = MutableLiveData<String>()
    val updateSuccess: LiveData<String> = _updateSuccess

    fun loadPost() {
        _isLoading.value = true
        repository.fetchAllPosts { result ->
            _isLoading.value = false
            result.onSuccess { postList -> _posts.value = postList }
            result.onFailure { e -> _error.value = e.message ?: "Неизвестная ошибка" }
        }
    }

    fun deletePost(postId: Int) {
        _isLoading.value = true
        repository.deletePost(postId) { result ->
            _isLoading.value = false
            result.onSuccess { _deleteSuccess.value = "Пост $postId успешно удалён" }
            result.onFailure { e -> _error.value = e.message }
        }
    }

    fun updatePost(postId: Int, newTitle: String, newBody: String) {
        _isLoading.value = true
        val postToUpdate = Post(id = postId, title = newTitle, body = newBody, userId = 1)
        repository.updatePost(postToUpdate) { result ->
            _isLoading.value = false
            result.onSuccess { updatePost -> _updateSuccess.value = "Пост ${updatePost.id} обновлён!" }
            result.onFailure { e -> _error.value = e.message }
        }

    }
}