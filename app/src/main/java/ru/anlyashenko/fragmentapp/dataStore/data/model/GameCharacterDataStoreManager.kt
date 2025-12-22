package ru.anlyashenko.fragmentapp.dataStore.data.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

val Context.gameCharacterDataStore: DataStore<GameCharacter> by dataStore(
    fileName = "character.json",
    serializer = CharacterSerializer
)

class GameCharacterDataStoreManager(
    private val dataStore: DataStore<GameCharacter>
) {

    val characterFlow: Flow<GameCharacter> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(GameCharacter())
            } else {
                throw exception
            }
        }

    suspend fun updateNickname(nickname: String) {
        dataStore.updateData { currentCharacter ->
            currentCharacter.copy(nickname = nickname)
        }
    }

    suspend fun levelUp() {
        dataStore.updateData { currentCharacter ->
            currentCharacter.copy(level = currentCharacter.level + 1)
        }
    }

    suspend fun addItem(item: String) {
        dataStore.updateData { currentCharacter ->
            currentCharacter.copy(
                inventory = currentCharacter.inventory + item
            )
        }
    }

    suspend fun removeItem(item: String) {
        dataStore.updateData { currentCharacter ->
            currentCharacter.copy(
                inventory = currentCharacter.inventory - item
            )
        }
    }

    suspend fun updateStats(
        hp: Int? = null,
        stamina: Int? = null,
        mana: Int? = null,
    ) {
        dataStore.updateData { currentCharacter ->
            currentCharacter.copy(
                stats = currentCharacter.stats.copy(
                    hp = hp ?: currentCharacter.stats.hp,
                    stamina = stamina ?: currentCharacter.stats.stamina,
                    mana = mana ?: currentCharacter.stats.mana
                )
            )
        }
    }

    suspend fun resetCharacter() {
        dataStore.updateData { GameCharacter() }
    }
}