package ru.anlyashenko.fragmentapp.dataStore.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.anlyashenko.fragmentapp.dataStore.Achievement
import ru.anlyashenko.fragmentapp.dataStore.AchievementStore
import ru.anlyashenko.fragmentapp.dataStore.data.model.AchievementDataStoreManager
import ru.anlyashenko.fragmentapp.dataStore.data.model.achievementStore

class AchievementsViewModel(
    private val manager: AchievementDataStoreManager
) : ViewModel() {

    val achievements: LiveData<List<Achievement>> = manager.achievementsFlow
        .map { store -> store.itemsList }
        .asLiveData()

    fun onAddAchievement(title: String, progress: Int = 0) {
        viewModelScope.launch {
            manager.addAchievement(title, progress)
        }
    }

    fun updateProgress(title: String, progress: Int) {
        viewModelScope.launch {
            manager.updateProgress(title, progress)
        }
    }

    fun removeAchievement(title: String) {
        viewModelScope.launch {
            manager.removeAchievement(title)
        }
    }

    fun onResetAll() {
        viewModelScope.launch {
            manager.clearAllAchievements()
        }
    }

}

class AchievementViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AchievementsViewModel::class.java) -> {
                val dataStore = context.applicationContext.achievementStore
                val manager = AchievementDataStoreManager(dataStore)
                AchievementsViewModel(manager) as T
            }
            else -> throw IllegalStateException("Unknow ViewModel class")
        }
    }
}