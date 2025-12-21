package ru.anlyashenko.fragmentapp

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.anlyashenko.fragmentapp.model.UserRole
import java.io.IOException
import kotlin.system.exitProcess

val Context.dataStoreUser by preferencesDataStore(name = "user_role")

data class UserDraft(
    val name: String = "",
    val notification: Boolean = false,
    val role: UserRole,
)

class DataStoreManagerRole(
    private val context: Context
) {
    companion object {
        val NAME_KEY = stringPreferencesKey("name_key")
        val NOTIFICATION_KEY = booleanPreferencesKey("notification_key")
        val ROLE_KEY = stringPreferencesKey("role_key")
    }

    val userRoleFlow: Flow<UserDraft> = context.dataStoreUser.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            UserDraft(
                name = preferences[NAME_KEY] ?: "",
                notification = preferences[NOTIFICATION_KEY] ?: false,
                role = preferences[ROLE_KEY]
                    ?.let { runCatching { UserRole.valueOf(it) }.getOrNull() }
                    ?: UserRole.GUEST
            )
        }

    suspend fun saveName(name: String) {
        context.dataStoreUser.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    suspend fun saveNotification(isEnabled: Boolean) {
        context.dataStoreUser.edit { preferences ->
            preferences[NOTIFICATION_KEY] = isEnabled
        }
    }

    suspend fun saveRole(role: UserRole) {
        context.dataStoreUser.edit { preferences ->
            preferences[ROLE_KEY] = role.name
        }
    }

    suspend fun clearAll() {
        context.dataStoreUser.edit { preferences ->
            preferences.clear()
        }
    }
}