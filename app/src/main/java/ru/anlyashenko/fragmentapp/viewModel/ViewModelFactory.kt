package ru.anlyashenko.fragmentapp.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.anlyashenko.fragmentapp.model.ServiceRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val appContext = context.applicationContext
        val repo = ServiceRepository(appContext)
        return ServiceViewModel(repo) as T
    }

}