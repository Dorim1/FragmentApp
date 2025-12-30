package ru.anlyashenko.fragmentapp.dataStore.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.anlyashenko.fragmentapp.dataStore.data.model.GameCharacterDataStoreManager
import ru.anlyashenko.fragmentapp.dataStore.data.model.gameCharacterDataStore
import ru.anlyashenko.fragmentapp.dataStore.data.repository.CharacterRepository

class GameCharacterViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GameCharacterViewModel::class.java) -> {
                val dataStore = context.applicationContext.gameCharacterDataStore
                val manager = GameCharacterDataStoreManager(dataStore)
                val repository = CharacterRepository(manager)
                GameCharacterViewModel(repository) as T
            }
            else -> throw IllegalStateException("Unknow ViewModel class")
        }
    }

}