package ru.anlyashenko.fragmentapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.anlyashenko.fragmentapp.model.PostRepository

class PostViewModel : ViewModel() {
    private val repository = PostRepository()
    private val _postTitle = MutableLiveData<String>()
    val postTitle: LiveData<String> = _postTitle
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadPost() {
        _isLoading.value = true
        repository.fetchPost(1) { result ->
            _isLoading.value = false
            result.onSuccess { post -> _postTitle.value = post.title }
            result.onFailure { e -> _error.value = e.message ?: "Неизвестная ошибка" }
        }
    }
}