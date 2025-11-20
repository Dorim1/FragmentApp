package ru.anlyashenko.fragmentapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ru.anlyashenko.fragmentapp.model.ServiceRepository

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    val progressBar = repository.progressState.asLiveData()
    val progress: LiveData<Int> = progressBar

    fun startWork() {
        repository.startService()
    }

    fun unbindingConnection() {
        repository.unbinding()
    }

    fun stopWork() {
        repository.stopService()
    }

    override fun onCleared() {
        super.onCleared()
    }

}