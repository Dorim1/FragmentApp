package ru.anlyashenko.fragmentapp.dataStore.data.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import ru.anlyashenko.fragmentapp.dataStore.Achievement
import ru.anlyashenko.fragmentapp.dataStore.AchievementStore
import ru.anlyashenko.fragmentapp.dataStore.achievement
import ru.anlyashenko.fragmentapp.dataStore.copy

val Context.achievementStore by dataStore(
    fileName = "achievement.pb",
    serializer = AchievementStoreSerializer
)

class AchievementDataStoreManager(
    private val dataStore: DataStore<AchievementStore>
) {

    val achievementsFlow: Flow<AchievementStore> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(AchievementStore.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun addAchievement(title: String, progress: Int = 0) {
        // если настроен Java Lite

        dataStore.updateData { currentStore ->
            val newAchievement = Achievement.newBuilder()
                .setTitle(title)
                .setProgress(progress)
                .build()

            currentStore.toBuilder()
                .addItems(newAchievement)
                .build()
        }

        // C Использование DSL (если настроен Kotlin Lite)

        /*dataStore.updateData { currentStore ->
            currentStore.copy {
                items.add(achievement {
                    this.title = title
                    this.progress = progress
                })
            }
        }*/


    }

    suspend fun updateProgress(title: String, newProgress: Int) {
        dataStore.updateData { currentStore ->
            val updatedItems = currentStore.itemsList.map { achievement ->
                if (achievement.title == title) {
                    achievement.toBuilder()
                        .setProgress(newProgress)
                        .build()
                } else {
                    achievement
                }
            }

            currentStore.toBuilder()
                .clearItems()
                .addAllItems(updatedItems)
                .build()
        }
    }

    suspend fun removeAchievement(title: String) {
        dataStore.updateData { currentStore ->
            val filteredItems = currentStore.itemsList.filter {
                it.title != title
            }

            currentStore.toBuilder()
                .clearItems()
                .addAllItems(filteredItems)
                .build()
        }
    }

    suspend fun clearAllAchievements() {
        dataStore.updateData {
            AchievementStore.getDefaultInstance()
        }
    }
}