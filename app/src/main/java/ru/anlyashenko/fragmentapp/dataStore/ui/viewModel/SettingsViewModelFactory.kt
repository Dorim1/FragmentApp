package ru.anlyashenko.fragmentapp.dataStore.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.anlyashenko.fragmentapp.dataStore.data.model.DataStoreManager
import ru.anlyashenko.fragmentapp.dataStore.data.model.DataStoreManagerRole

class SettingsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsDataStoreViewModel::class.java)) {
            val manager = DataStoreManager(context)
            val role = DataStoreManagerRole(context)
            @Suppress("UNCHECKED_CAST")
            return SettingsDataStoreViewModel(manager, role) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}