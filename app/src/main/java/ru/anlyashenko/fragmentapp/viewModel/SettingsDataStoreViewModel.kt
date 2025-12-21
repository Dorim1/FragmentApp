package ru.anlyashenko.fragmentapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anlyashenko.fragmentapp.DataStoreManager
import ru.anlyashenko.fragmentapp.DataStoreManagerRole
import ru.anlyashenko.fragmentapp.UserDraft
import ru.anlyashenko.fragmentapp.UserPreferences
import ru.anlyashenko.fragmentapp.model.UserRole

class SettingsDataStoreViewModel(
    private val dataStoreManager: DataStoreManager,
    private val dataStoreManagerRole: DataStoreManagerRole,
) : ViewModel() {

    val settings: LiveData<UserPreferences> = dataStoreManager.userPreferencesFlow.asLiveData()
    val role: LiveData<UserDraft> = dataStoreManagerRole.userRoleFlow.asLiveData()

    fun onShowInStockChanged(isChecked: Boolean) {
        viewModelScope.launch {
            dataStoreManager.updateShowInStock(isChecked)
        }
    }

    fun onSortKeyChanged(newSortKey: String) {
        viewModelScope.launch {
            dataStoreManager.updateSortKey(newSortKey)
        }
    }

    fun onMinPriceChanged(newMinPrice: Int) {
        viewModelScope.launch {
            dataStoreManager.updateMinPrice(newMinPrice)
        }
    }

    fun onNameChanged(text: String) {
        viewModelScope.launch {
            dataStoreManagerRole.saveName(text)
        }
    }

    fun onRoleChanged(role: UserRole) {
        viewModelScope.launch {
            dataStoreManagerRole.saveRole(role)
        }
    }

    fun onNotificationChanged(bool: Boolean) {
        viewModelScope.launch {
            dataStoreManagerRole.saveNotification(bool)
        }
    }

    fun onClearClicked() {
        viewModelScope.launch {
            dataStoreManagerRole.clearAll()
            dataStoreManager.clearAll()
        }
    }

}