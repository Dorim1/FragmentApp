package ru.anlyashenko.fragmentapp.viewModel

import android.app.usage.UsageEvents
import android.service.autofill.UserData
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.anlyashenko.fragmentapp.model.Post
import ru.anlyashenko.fragmentapp.model.PostRepository
import ru.anlyashenko.fragmentapp.model.User
import ru.anlyashenko.fragmentapp.retrofit.AuthRepository
import ru.anlyashenko.fragmentapp.retrofit.Product
import ru.anlyashenko.fragmentapp.retrofit.ProductRepository
import ru.anlyashenko.fragmentapp.retrofit.ProductResponse
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
    private val _createSuccess = MutableLiveData<String>()
    val createSuccess: LiveData<String> = _createSuccess





        // Retrofit
    private val productRepository = ProductRepository()
    private val repositoryAuth = AuthRepository()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<String>()
    val product: LiveData<String> = _product

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
            result.onSuccess {
                _posts.value = _posts.value?.filterNot { it.id == postId }
                _deleteSuccess.value = "Пост $postId успешно удалён" }
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

    fun createPost(title: String, body: String) {
        _isLoading.value = true
        val newPost = Post(id = 0, userId = 1, title = title, body = body)
        repository.createPost(newPost) {result ->
            _isLoading.value = false
            result.onSuccess { createdPost ->
                val updateList = _posts.value.orEmpty().toMutableList().apply {
                    add(0, createdPost)
                }
                _posts.postValue(updateList)
                _createSuccess.postValue("Пост ${createdPost.title} создан!")
            }
            result.onFailure { e -> _error.value = e.message }
        }
    }







    // Retrofit

    fun loadProducts() {
        _isLoading.value = true
        productRepository.fetchAllProducts { result ->
            _isLoading.value = false
            result.onSuccess { productsList ->  _products.value = productsList}
            result.onFailure { e -> _error.value = e.message ?: "Неизвестная ошибка" }
        }
    }

    fun loadProductById(id: Int) {
        _isLoading.value = true
        productRepository.fetchProductById(id) { result ->
            _isLoading.value = false
            result.onSuccess { product -> _product.value = "${product.title}\n${product.category}\n${product.description}" }
            result.onFailure { e -> _error.value = e.message ?: "Неизвестная ошибка" }
        }
    }

    fun signIn(username: String, password: String) {
        repositoryAuth.signIn(username, password) { result ->
            result.onSuccess { userData -> _user.postValue(userData) }
            result.onFailure { e -> _error.postValue(e.message) }
        }
    }

    fun loadBySearch(name: String) {
        _isLoading.value = true
        productRepository.signIn { tokenResult ->
            tokenResult.onSuccess { token ->
                productRepository.fetchProductBySearch(token, name) { searchResult ->
                    _isLoading.value = false
                    searchResult.onSuccess { productsList -> _products.value = productsList }
                    searchResult.onFailure { e -> _error.value = e.message ?: "Неизвестная ошибка" }
                }
            }
            tokenResult.onFailure { e -> _error.value = e.message }
        }

    }
}