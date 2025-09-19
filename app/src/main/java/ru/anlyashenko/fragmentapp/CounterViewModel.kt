package ru.anlyashenko.fragmentapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CounterViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        const val KEY_COUNTER = "key_counter"
    }

    private var _counter: MutableLiveData<Int> = savedStateHandle.getLiveData(KEY_COUNTER, 0)
    val counter: LiveData<Int> = _counter

    fun increment() {
        _counter.value = (_counter.value ?: 0) + 1
    }

}