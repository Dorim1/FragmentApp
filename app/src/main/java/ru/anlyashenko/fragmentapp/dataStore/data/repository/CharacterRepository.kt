package ru.anlyashenko.fragmentapp.dataStore.data.repository

import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.fragmentapp.dataStore.data.model.GameCharacter
import ru.anlyashenko.fragmentapp.dataStore.data.model.GameCharacterDataStoreManager

class CharacterRepository(private val dataStoreManager: GameCharacterDataStoreManager) {

    val character: Flow<GameCharacter> = dataStoreManager.characterFlow

    suspend fun updateNickname(nickname: String) {
        dataStoreManager.updateNickname(nickname)
    }

    suspend fun levelUp() {
        dataStoreManager.levelUp()
    }

    suspend fun addItemToInventory(item: String) {
        dataStoreManager.addItem(item)
    }

    suspend fun removeItemFromInventory(item: String) {
        dataStoreManager.removeItem(item)
    }

    suspend fun updateCharacterStats(hp: Int? = null, stamina: Int? = null, mana: Int? = null) {
        dataStoreManager.updateStats(hp, stamina, mana)
    }

    suspend fun resetCharacter() {
        dataStoreManager.resetCharacter()
    }


}