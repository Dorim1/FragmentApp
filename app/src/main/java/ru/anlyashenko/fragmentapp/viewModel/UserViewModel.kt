package ru.anlyashenko.fragmentapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.anlyashenko.fragmentapp.model.UserNet
import ru.anlyashenko.fragmentapp.model.UserRepository

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _user = MutableLiveData<UserNet>()
    val user: LiveData<UserNet> = _user

    private val _displayText = MutableLiveData<String>()
    val displayText: LiveData<String> = _displayText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadUser() {
        _isLoading.value = true
        repository.fetchUser(1) {result ->
            _isLoading.value = false
            result.onSuccess { user ->
                _user.value = user.copy(id = 0)
                _displayText.value = "${user.name}\n${user.email}\n${user.username}"
            }
            result.onFailure { e -> _error.value = e.message ?: "Ошибка" }
        }
    }
}