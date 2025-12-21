package ru.anlyashenko.fragmentapp

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "settings")

data class UserPreferences(
    val sortKey: String = "",
    val showInStock: Boolean = false,
    val minPrice: Int = 0
)
class DataStoreManager(
    private val context: Context
) {
    companion object {
        val SORT_KEY = stringPreferencesKey("sort_key")
        val SHOW_IN_STOCK_KEY = booleanPreferencesKey("show_in_stock")
        val MIN_PRICE_KEY = intPreferencesKey("min_price")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
    .map { preferences ->
        UserPreferences(
        preferences[SORT_KEY] ?: "",
        preferences[SHOW_IN_STOCK_KEY] ?: false,
        preferences[MIN_PRICE_KEY] ?: 0
        )
    }

    suspend fun updateSortKey(value: String) {
        context.dataStore.edit { preferences ->
            preferences[SORT_KEY] = value
        }
    }

    suspend fun updateShowInStock(isShow: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_IN_STOCK_KEY] = isShow
        }
    }

    suspend fun updateMinPrice(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[MIN_PRICE_KEY] = value
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}