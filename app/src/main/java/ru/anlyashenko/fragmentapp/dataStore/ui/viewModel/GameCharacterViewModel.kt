package ru.anlyashenko.fragmentapp.dataStore.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anlyashenko.fragmentapp.dataStore.data.model.GameCharacter
import ru.anlyashenko.fragmentapp.dataStore.data.repository.CharacterRepository

class GameCharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    val character: LiveData<GameCharacter> = repository.character.asLiveData()

    fun updateNickname(nickname: String) {
        viewModelScope.launch {
            repository.updateNickname(nickname)
        }
    }

    fun levelUp() {
        viewModelScope.launch {
            repository.levelUp()
        }
    }

    fun addItem(item: String) {
        viewModelScope.launch {
            repository.addItemToInventory(item)
        }
    }

    fun removeItem(item: String) {
        viewModelScope.launch {
            repository.removeItemFromInventory(item)
        }
    }

    fun updateStats(hp: Int? = null, stamina: Int? = null, mana: Int? = null ) {
        viewModelScope.launch {
            repository.updateCharacterStats(hp, stamina, mana)
        }
    }

    fun resetCharacter() {
        viewModelScope.launch {
            repository.resetCharacter()
        }
    }
}