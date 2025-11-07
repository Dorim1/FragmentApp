package ru.anlyashenko.fragmentapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.anlyashenko.fragmentapp.model.Post
import ru.anlyashenko.fragmentapp.model.PostRepository

class PostViewModel : ViewModel() {
    private val repository = PostRepository()
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadPost() {
        _isLoading.value = true
        repository.fetchAllPosts { result ->
            _isLoading.value = false
            result.onSuccess { postList -> _posts.value = postList }
            result.onFailure { e -> _error.value = e.message ?: "Неизвестная ошибка" }
        }
    }
}